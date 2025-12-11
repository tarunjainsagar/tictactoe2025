package org.example.tictactoe.models;

public class Player {

    private String name;

    private char symbol;

    private PlayerType playerType;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.playerType = PlayerType.HUMAN;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
}
