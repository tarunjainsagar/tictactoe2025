package org.example.tictactoe.dtos;

import org.example.tictactoe.models.Player;

import java.util.List;

public class GameStartRequestDto {

    private int dimension;

    private List<Player> players;

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
