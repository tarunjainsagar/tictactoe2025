package org.example.tictactoe.strategies.winning;

import org.example.tictactoe.models.Board;
import org.example.tictactoe.models.Cell;
import org.example.tictactoe.models.Player;

import java.util.List;

public class LeftDiagonalWinningStrategy implements WinningStrategy {

    @Override
    public boolean isWinner(Board board, Cell cell, Player player) {
        int currRow = cell.getRow();
        int currCol = cell.getCol();

        if (currRow != currCol) {
            // Current move is not made on diagonal, so no point of checking it
            return false;
        }

        int dimension = board.getDimension();

        List<List<Cell>> cells = board.getCells();
        for (int idx = 0; idx < dimension; idx++) {
            if (player.getSymbol() != cells.get(idx).get(idx).getSymbol()) {
                // return early if a mismatch is found
                return false;
            }
        }
        return true;
    }

}
