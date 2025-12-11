package org.example.tictactoe.strategies.winning;

import org.example.tictactoe.models.Board;
import org.example.tictactoe.models.Cell;
import org.example.tictactoe.models.Player;

import java.util.List;

public class RightDiagonalWinningStrategy implements  WinningStrategy {

    @Override
    public boolean isWinner(Board board, Cell cell, Player player) {
        int currRow = cell.getRow();
        int currCol = cell.getCol();
        int dimension = board.getDimension();

        if (currRow != dimension - currCol - 1) {
            // Current move is not made on diagonal, so no point of checking it
            return false;
        }

        List<List<Cell>> cells = board.getCells();
        for (int idx = 0; idx < dimension; idx++) {
            if (player.getSymbol() != cells.get(idx).get(dimension - idx - 1).getSymbol()) {
                // return early if a mismatch is found
                return false;
            }
        }
        return true;
    }

}
