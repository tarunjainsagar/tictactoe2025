package org.example.tictactoe.controllers;

import org.example.tictactoe.dtos.*;
import org.example.tictactoe.exceptions.*;
import org.example.tictactoe.models.Game;
import org.example.tictactoe.models.GameState;
import org.example.tictactoe.models.Player;
import org.example.tictactoe.services.GameService;

public class GameController {

    private GameService gameService;

    public GameController() {
        this.gameService = new GameService();
    }

    public GameStartResponseDto startGame(GameStartRequestDto request) {
        GameStartResponseDto response = new GameStartResponseDto();

        try {
            Game game = gameService.start(request.getDimension(), request.getPlayers());
            response.setStatus(ResponseStatus.SUCCESS);
            response.setGameId(game.getId());
        } catch (Exception e) {
            response.setStatus(ResponseStatus.FAILURE);
        }

        return response;
    }

    public GameMoveResponseDto makeMove(GameRequestDto gameRequestDto) {
        GameMoveResponseDto response = new GameMoveResponseDto();
        try {
            Game game = gameService.makeMove(gameRequestDto.getGameId());
            response.setGameState(game.getGameState());
        } catch (InvalidGameIdException e) {
            response.setMessage("Game with given gameId is not found");
            response.setStatus(ResponseStatus.FAILURE);
        } catch (MoveNotAllowedException e) {
            response.setMessage("Move not found: " + e.getMessage());
            response.setStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    public GameResponseDto undoLastPlayerMove(GameRequestDto gameRequestDto) {
        GameResponseDto response = new GameResponseDto();
        try {
            gameService.undoLastPlayerMove(gameRequestDto.getGameId());
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (InvalidGameIdException e) {
            response.setMessage("Game with given gameId is not found");
            response.setStatus(ResponseStatus.FAILURE);
        } catch (InvalidActionException e) {
            response.setMessage("Could not perform undo operation. " + e.getMessage());
            response.setStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    public GameStateResponseDto getGameState(GameRequestDto gameRequestDto) {
        GameStateResponseDto response = new GameStateResponseDto();
        try {
            GameState gameState = gameService.getGameState(gameRequestDto.getGameId());
            response.setGameState(gameState);
        } catch (InvalidGameIdException e) {
            response.setStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    public GameResponseDto displayBoard(GameRequestDto gameRequestDto) {

        GameResponseDto response = new GameResponseDto();
        try {
            gameService.displayBoard(gameRequestDto.getGameId());
            response.setStatus(ResponseStatus.SUCCESS);
        } catch (InvalidGameIdException e) {
            response.setMessage("Game with given gameId is not found");
            response.setStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    public GamePlayerResponseDto getWinner(GameRequestDto gameRequestDto) {
        GamePlayerResponseDto response = new GamePlayerResponseDto();
        try {
            Player player = gameService.getWinner(gameRequestDto.getGameId());
            response.setGameState(GameState.WON);
            response.setPlayer(player);
        } catch (InPlayWinnerNotFoundException e) {
            response.setGameState(GameState.IN_PLAY);
            response.setStatus(ResponseStatus.FAILURE);
        } catch (DrawnWinnerNotFoundException e) {
            response.setGameState(GameState.DRAW);
            response.setStatus(ResponseStatus.FAILURE);
        } catch (InvalidGameIdException e) {
            response.setStatus(ResponseStatus.FAILURE);
        }
        return response;
    }

    public GamePlayerResponseDto getNextPlayer(GameRequestDto gameRequestDto) {
        GamePlayerResponseDto response = new GamePlayerResponseDto();
        try {
            Player player = gameService.getNextPlayer(gameRequestDto.getGameId());
            response.setGameState(GameState.IN_PLAY);
            response.setPlayer(player);
        } catch (PlayerNotFoundException | InvalidGameIdException e) {
            response.setStatus(ResponseStatus.FAILURE);
        }
        return response;
    }
}
