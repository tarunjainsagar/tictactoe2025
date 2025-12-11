package org.example.tictactoe.models;

public class Cell {

    private int row;

    private int col;

    private Player player;

    private char symbol;

    private CellState cellState;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellState = CellState.EMPTY;
    }

    public boolean isEmpty() {
        return this.cellState == CellState.EMPTY;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Player getPlayer() {
        return player;
    }

    public Cell setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public char getSymbol() {
        return symbol;
    }

    public Cell setSymbol(char symbol) {
        this.symbol = symbol;
        return this;
    }

    public CellState getCellState() {
        return cellState;
    }

    public Cell setCellState(CellState cellState) {
        this.cellState = cellState;
        return this;
    }

    public int getHumanReadableCellNumber(int dimension) {
        return this.getRow() * dimension + this.getCol() + 1;
    }
}
