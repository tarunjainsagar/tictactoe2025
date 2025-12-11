package org.example.tictactoe.dtos;

public class GameRequestDto {

    private int gameId;

    public GameRequestDto(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
