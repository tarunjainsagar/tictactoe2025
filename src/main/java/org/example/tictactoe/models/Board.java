package org.example.tictactoe.models;

import org.example.tictactoe.utils.CharacterConvertorUtils;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int dimension;

    private List<List<Cell>> cells;

    public Board(int dimension) {
        this.dimension = dimension;

        cells = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {

            List<Cell> row = new ArrayList<>();
            for (int j = 0; j < dimension; j++) {
                Cell cell = new Cell(i, j);
                row.add(cell);
            }
            cells.add(row);
        }
    }

    public int getDimension() {
        return dimension;
    }

    public Board setDimension(int dimension) {
        this.dimension = dimension;
        return this;
    }

    public List<List<Cell>> getCells() {
        return cells;
    }

    public Board setCells(List<List<Cell>> cells) {
        this.cells = cells;
        return this;
    }

    public void display() {
        final String GREY = "\u001B[90m";
        final String RESET = "\u001B[0m";

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                Cell cell = cells.get(i).get(j);
                if (cell.isEmpty()) {
                    int cellNumber = cell.getHumanReadableCellNumber(dimension);
                    String sub = CharacterConvertorUtils.toSubscript(cellNumber);
                    System.out.print(" [ " + GREY + sub + RESET + " ] ");
                } else {
                    System.out.print(" [ " + cell.getSymbol() + " ] ");
                }

            }
            System.out.println();
        }
    }

    public boolean isCellExists(int humanReadableCellNumber) {
        int index = humanReadableCellNumber - 1;
        return index >= 0 && index < (this.getDimension() * this.getDimension());
    }

    public Cell getCell(int humanReadableCellNumber) {
        // Convert 1-based number → 0-based index
        int index = humanReadableCellNumber - 1;

        // Compute row and column
        int targetRow = index / this.getDimension();
        int targetCol = index % this.getDimension();
        return this.getCells().get(targetRow).get(targetCol);
    }

    public boolean isAllCellFilled(Game game) {

        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (cells.get(row).get(col).getCellState() == CellState.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
