package org.example.tictactoe.dtos;

import org.example.tictactoe.models.GameState;
import org.example.tictactoe.models.Player;

public class GamePlayerResponseDto {

    private Player player;
    private GameState gameState;
    private ResponseStatus status;

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }
}
