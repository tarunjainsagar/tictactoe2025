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
        cell = cell.markFilled(player);

        Move move = new Move(cell, player);
        game.updateMoves(move);

        Cell lastMoveCell = cell;
        game = afterMoveChecks(game, board, lastMoveCell, player, playerIdx);

        // Check for Bot Player
        Player nextPlayer = game.getPlayers().get(game.getNextPlayerTurnIdx());
        game = makeBotMove(nextPlayer, board, game, game.getNextPlayerTurnIdx());
        return game;
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
        int nextPlayerIdx = ((currentPlayerIdx - 1 + totalPlayers) % totalPlayers);
        game.setNextPlayerTurnIdx(nextPlayerIdx);

        int cellNumber = lastMoveCell.getHumanReadableCellNumber(game.getBoard().getDimension());
        Player currentPlayer = game.getPlayers().get(currentPlayerIdx);
        System.out.println("Last Move at Cell: " + cellNumber + " is undone by Player: " + currentPlayer.getName());

        Board board = game.getBoard();
        // Check for Bot Player
        Player nextPlayer = game.getPlayers().get(nextPlayerIdx);
        game = makeBotMove(nextPlayer, board, game, nextPlayerIdx);
    }

    private Game makeBotMove(Player nextPlayer, Board board, Game game, int playerIdx) {
        if (nextPlayer.getPlayerType() == PlayerType.BOT) {
            board.display();
            System.out.println("======================================");
            System.out.println("Bot's turn. Bot is making a move ... : ");
            BotPlayer botPlayer = (BotPlayer) nextPlayer;
            game = botPlayer.getBotPlayingStrategy().makeMove(game, botPlayer);

            Cell botCell = game.getMoves().getLast().getCell();
            System.out.println("Bot played on cell number: " + botCell.getHumanReadableCellNumber(board.getDimension()));

            game = afterMoveChecks(game, board, botCell, botPlayer, playerIdx);
        }
        return game;
    }

    private Game afterMoveChecks(Game game, Board board, Cell lastMoveCell, Player player, int playerIdx) {
        boolean hasWinner = game.getWinningStrategy().stream().anyMatch(s -> s.isWinner(board, lastMoveCell, player));
        if (hasWinner) {
            // Check winner
            game.setGameState(GameState.WON);
            game.setWinner(player);
            game = gameRepository.save(game);
        } else if (board.isAllCellFilled(game)) {
            // Check drawn
            game.setGameState(GameState.DRAW);
            game = gameRepository.save(game);
        } else {
            // Update next player
            int nextPlayerIdx = (playerIdx + 1) % game.getPlayers().size();
            game.setNextPlayerTurnIdx(nextPlayerIdx);

            // Save Player's move
            game = gameRepository.save(game);
        }
        return game;
    }
}
