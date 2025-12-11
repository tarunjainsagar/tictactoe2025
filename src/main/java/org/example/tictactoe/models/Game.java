package org.example.tictactoe.models;

import org.example.tictactoe.dtos.ResponseStatus;
import org.example.tictactoe.dtos.ValidatorResponse;
import org.example.tictactoe.exceptions.InvalidGameInputException;
import org.example.tictactoe.strategies.winning.*;
import org.example.tictactoe.validators.gamebuild.DimensionGameBuildValidator;
import org.example.tictactoe.validators.gamebuild.GameBuildValidator;
import org.example.tictactoe.validators.gamebuild.PlayerGameBuildValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private int id;

    private Board board;

    private List<Player> players;

    private List<Move> moves;

    private int nextPlayerTurnIdx;

    private GameState gameState;

    private Player winner;

    private List<WinningStrategy> winningStrategy;

    public int getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public int getNextPlayerTurnIdx() {
        return nextPlayerTurnIdx;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getWinner() {
        return winner;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game setBoard(Board board) {
        this.board = board;
        return this;
    }

    public Game setPlayers(List<Player> players) {
        this.players = players;
        return this;
    }

    public Game setMoves(List<Move> moves) {
        this.moves = moves;
        return this;
    }

    public Game updateMoves(Move move) {
        this.moves.add(move);
        return this;
    }

    public Game setNextPlayerTurnIdx(int nextPlayerTurnIdx) {
        this.nextPlayerTurnIdx = nextPlayerTurnIdx;
        return this;
    }

    public Game setGameState(GameState gameState) {
        this.gameState = gameState;
        return this;
    }

    public Game setWinner(Player winner) {
        this.winner = winner;
        return this;
    }

    public List<WinningStrategy> getWinningStrategy() {
        return winningStrategy;
    }

    public Game setWinningStrategy(List<WinningStrategy> winningStrategy) {
        this.winningStrategy = winningStrategy;
        return this;
    }

    private Game() {
        /*
         * Making it impossible to directly create object of Game class
         */
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public static class GameBuilder {

        private int dimension;

        private List<Player> players;

        // TODO: hardcoded right now, later we can use reflection
        private List<GameBuildValidator> validators = Arrays.asList(new DimensionGameBuildValidator(), new PlayerGameBuildValidator());

        private GameBuilder() {
            /*
             * Making it impossible to directly create object of GameBuilder class
             */
        }

        public int getDimension() {
            return dimension;
        }

        public GameBuilder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public List<Player> getPlayers() {
            return players;
        }

        public GameBuilder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        private ValidatorResponse validate() throws InvalidGameInputException {
            for (GameBuildValidator gbv : validators) {
                ValidatorResponse response = gbv.validate(this);
                if (response.getStatus() == ResponseStatus.FAILURE) {
                    throw new InvalidGameInputException(response.getMessage());
                }
            }
            return new ValidatorResponse(ResponseStatus.SUCCESS, "All validations passed");
        }

        public Game build() throws InvalidGameInputException {

            validate();
            Game game = new Game()
                    .setBoard(new Board(dimension))
                    .setPlayers(players)
                    .setGameState(GameState.IN_PLAY)
                    .setWinningStrategy(
                            // TODO: Hardcoding winning strategies for now,
                            //  later will make it dynamic if required
                            Arrays.asList(
                                    new RowWinningStrategy(),
                                    new ColumnWinningStrategy(),
                                    new LeftDiagonalWinningStrategy(),
                                    new RightDiagonalWinningStrategy()))
                    .setNextPlayerTurnIdx((int) (Math.random() * players.size()))
                    .setMoves(new ArrayList<>())
                    .setWinner(null);

            return game;
        }

    }

}
