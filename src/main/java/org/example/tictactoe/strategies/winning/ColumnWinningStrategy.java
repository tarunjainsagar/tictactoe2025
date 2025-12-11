package org.example.tictactoe.strategies.winning;

import org.example.tictactoe.models.Board;
import org.example.tictactoe.models.Cell;
import org.example.tictactoe.models.Player;

import java.util.List;

public class ColumnWinningStrategy implements  WinningStrategy {

    @Override
    public boolean isWinner(Board board, Cell cell, Player player) {
        int col = cell.getCol();
        int dimension = board.getDimension();

        List<List<Cell>> cells = board.getCells();
        for (int row = 0; row < dimension; row++) {
            if (player.getSymbol() != cells.get(row).get(col).getSymbol()) {
                // return early if a mismatch is found
                return false;
            }
        }
        return true;
    }
}
