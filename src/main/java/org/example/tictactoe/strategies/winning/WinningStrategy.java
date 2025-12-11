package org.example.tictactoe.strategies.winning;

import org.example.tictactoe.models.Board;
import org.example.tictactoe.models.Cell;
import org.example.tictactoe.models.Player;

public interface WinningStrategy {
    boolean isWinner(Board board, Cell cell, Player player);
}
