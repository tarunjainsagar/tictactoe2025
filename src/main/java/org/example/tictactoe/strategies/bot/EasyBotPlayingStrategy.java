package org.example.tictactoe.strategies.bot;

import org.example.tictactoe.models.*;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy {

    @Override
    public Game makeMove(Game game, BotPlayer botPlayer) {

        Board board = game.getBoard();
        List<List<Cell>> cells = board.getCells();
        int dimension = board.getDimension();

        int[] firstEmptyCell = findFirstEmptyCell(dimension, cells);
        if (firstEmptyCell == null) {
            return game;
        }

        Cell cell = cells.get(firstEmptyCell[0]).get(firstEmptyCell[1]);
        cell = cell.markFilled(botPlayer);

        Move move = new Move(cell, botPlayer);
        game.updateMoves(move);
        return game;
    }

    private int[] findFirstEmptyCell(int dimension, List<List<Cell>> cells) {
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (cells.get(row).get(col).getCellState() == CellState.EMPTY) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }
}
