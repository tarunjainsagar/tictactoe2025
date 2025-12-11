package org.example.tictactoe.services;

import org.example.tictactoe.exceptions.*;
import org.example.tictactoe.models.Game;
import org.example.tictactoe.models.GameState;
import org.example.tictactoe.models.Player;
import org.example.tictactoe.repositories.GameRepository;
import org.example.tictactoe.repositories.GameRepositoryImpl;
import org.example.tictactoe.helpers.GameExecutionHelper;

import java.util.List;

import static org.example.tictactoe.models.GameState.DRAW;
import static org.example.tictactoe.models.GameState.IN_PLAY;

public class GameService {

    private final GameRepository gameRepository;
    private final GameExecutionHelper gameExecutionHelper;

    public GameService() {
        this.gameRepository = new GameRepositoryImpl();
        this.gameExecutionHelper = new GameExecutionHelper();
    }

    public Game start(int dimension, List<Player> players) throws InvalidGameInputException {
        Game game = Game.builder()
                .setDimension(dimension)
                .setPlayers(players)
                .build();
        game = gameRepository.save(game);
        return game;
    }

    public Game makeMove(int gameId) throws InvalidGameIdException, MoveNotAllowedException {
        Game game = getGame(gameId);
        return gameExecutionHelper.makeMove(game);
    }

    public void undoLastPlayerMove(int gameId) throws InvalidGameIdException, InvalidActionException {
        Game game = getGame(gameId);
        gameExecutionHelper.revertLastMove(game);
    }

    public GameState getGameState(int gameId) throws InvalidGameIdException {
        Game game = getGame(gameId);
        return game.getGameState();
    }

    public void displayBoard(int gameId) throws InvalidGameIdException {
        Game game = getGame(gameId);
        game.getBoard().display();
    }

    public Player getWinner(int gameId) throws InvalidGameIdException, InPlayWinnerNotFoundException, DrawnWinnerNotFoundException {
        Game game = getGame(gameId);
        GameState gameState = game.getGameState();
        if (gameState == IN_PLAY) {
            throw new InPlayWinnerNotFoundException("Game is in play. No winner found");
        } else if (gameState == DRAW) {
            throw new DrawnWinnerNotFoundException("Game is drawn. No winner found");
        }
        return game.getWinner();
    }

    private Game getGame(int gameId) throws InvalidGameIdException {
        return gameRepository.findById(gameId).orElseThrow(() -> new InvalidGameIdException("Game not found"));
    }

    public Player getNextPlayer(int gameId) throws InvalidGameIdException, PlayerNotFoundException {
        Game game = getGame(gameId);
        GameState gameState = game.getGameState();
        if (gameState != IN_PLAY) {
            throw new PlayerNotFoundException("No current player found");
        }

        return game.getPlayers().get(game.getNextPlayerTurnIdx());
    }
}
