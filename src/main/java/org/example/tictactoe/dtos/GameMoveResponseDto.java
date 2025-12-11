package org.example.tictactoe.dtos;

import org.example.tictactoe.models.GameState;

public class GameMoveResponseDto {

    private GameState gameState;
    private String message;
    private ResponseStatus status;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
