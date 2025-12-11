package org.example.tictactoe.strategies.winning;

import org.example.tictactoe.models.Board;
import org.example.tictactoe.models.Cell;
import org.example.tictactoe.models.Player;

import java.util.List;

public class RowWinningStrategy implements WinningStrategy {

    @Override
    public boolean isWinner(Board board, Cell cell, Player player) {
        int row = cell.getRow();
        int dimension = board.getDimension();

        List<List<Cell>> cells = board.getCells();
        for (int col = 0; col < dimension; col++) {
            if (player.getSymbol() != cells.get(row).get(col).getSymbol()) {
                // return early if a mismatch is found
                return false;
            }
        }
        return true;
    }
}
