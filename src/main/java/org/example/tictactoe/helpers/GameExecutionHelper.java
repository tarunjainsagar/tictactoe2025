package org.example.tictactoe.helpers;

import org.example.tictactoe.exceptions.InvalidActionException;
import org.example.tictactoe.exceptions.MoveNotAllowedException;
import org.example.tictactoe.models.*;
import org.example.tictactoe.repositories.GameRepository;
import org.example.tictactoe.repositories.GameRepositoryImpl;

import java.util.List;
import java.util.Scanner;

public class GameExecutionHelper {

    private final GameRepository gameRepository;
    private final Scanner in = new Scanner(System.in);

    public GameExecutionHelper() {
        gameRepository = new GameRepositoryImpl();
    }

    public Game makeMove(Game game) throws MoveNotAllowedException {
        // Validation
        if (game.getGameState() != GameState.IN_PLAY) {
            throw new MoveNotAllowedException("Game is not in play.");
        }

        // Get move input
        int playerIdx = game.getNextPlayerTurnIdx();
        Player player = game.getPlayers().get(playerIdx);
        System.out.println("Enter an empty cell number on the board.");
        System.out.println("It is " + player.getName() + " [" + player.getSymbol() + "] turn: ");
        int cellInput = in.nextInt();

        Board board = game.getBoard();
        while (!(board.isCellExists(cellInput) && board.getCell(cellInput).isEmpty())) {
            System.out.println("Invalid input. Enter an empty cell number on the board.");
            System.out.println("It is " + player.getName() + " [" + player.getSymbol() + "] turn: ");
            cellInput = in.nextInt();
        }

        // Make Move - Updating cell's data in the board object itself
        Cell cell = board.getCell(cellInput);
        cell.setPlayer(player);
        cell.setCellState(CellState.FILLED);
        cell.setSymbol(player.getSymbol());

        Move move = new Move(cell, player);
        game.updateMoves(move);

        // Check winner
        game.getWinningStrategy().stream()
                .filter(s -> s.isWinner(board, cell, player))
                .findFirst()
                .ifPresent(s -> {
                    game.setGameState(GameState.WON);
                    game.setWinner(player);
                });

        // Check drawn
        if(board.isAllCellFilled(game)) {
            game.setGameState(GameState.DRAW);
        }

        // Update next player
        game.setNextPlayerTurnIdx((playerIdx + 1) % game.getPlayers().size());

        return gameRepository.save(game);
    }

    public void revertLastMove(Game game) throws InvalidActionException {
        List<Move> moves = game.getMoves();

        if (moves.isEmpty()) {
            throw new InvalidActionException("No moves found to undo.");
        }

        Move lastMove = moves.getLast();

        Cell lastMoveCell = lastMove.getCell();
        lastMoveCell.setCellState(CellState.EMPTY);
        lastMoveCell.setPlayer(null);
        lastMoveCell.setSymbol(' ');

        moves.removeLast();
        game.setMoves(moves);

        int currentPlayerIdx = game.getNextPlayerTurnIdx();
        int totalPlayers = game.getPlayers().size();
        // Previous player
        int updatedPlayerTurnIdx = ((currentPlayerIdx - 1 + totalPlayers) % totalPlayers);
        game.setNextPlayerTurnIdx(updatedPlayerTurnIdx);

        int cellNumber = lastMoveCell.getHumanReadableCellNumber(game.getBoard().getDimension());
        Player currentPlayer = game.getPlayers().get(currentPlayerIdx);
        System.out.println("Last Move at Cell: " + cellNumber + " is undone by Player: " + currentPlayer.getName());
    }

}
