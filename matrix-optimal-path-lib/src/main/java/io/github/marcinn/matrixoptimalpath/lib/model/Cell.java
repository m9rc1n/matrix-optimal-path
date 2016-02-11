package io.github.marcinn.matrixoptimalpath.lib.model;

import java.util.ArrayList;

public class Cell implements Comparable<Cell> {

    private final int row;
    private final int column;
    private final int index;
    private final int value;
    private final ArrayList<Integer> neighbours;
    private int cost;
    private Cell previous;

    public Cell(int value, int index, MatrixInfo info) {
        if (index >= info.getLength()) {
            throw new IllegalArgumentException("Index should not be bigger that matrix size");
        }
        this.value = value;
        this.cost = Integer.MAX_VALUE;
        this.index = index;
        this.row = index / info.getColumnNumber();
        this.column = index % info.getColumnNumber();
        this.neighbours = generateNeighbours(info);
        this.previous = null;
    }

    private ArrayList<Integer> generateNeighbours(MatrixInfo info) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int newR = row + i;
                int newC = column + j;
                if (isSameCell(newR, newC)) continue;
                if (isOutOfMatrix(info, newR, newC)) continue;
                result.add(newR * info.getColumnNumber() + newC);
            }
        }
        return result;
    }

    private boolean isOutOfMatrix(MatrixInfo info, int newR, int newC) {
        return isOutOfLastRow(info, newR, newC) || isOutOfMatrixWithoutLastRow(info, newR, newC);
    }

    private boolean isOutOfMatrixWithoutLastRow(MatrixInfo info, int newR, int newC) {
        return newR < 0 || newC < 0 || newC >= info.getColumnNumber() || newR >= info.getRowNumber();
    }

    private boolean isSameCell(int newR, int newC) {
        return newC == column && newR == row;
    }

    private boolean isOutOfLastRow(MatrixInfo info, int newR, int newC) {
        int lastRow = info.getLastRowColumnNumber();
        return lastRow != 0 && newR == info.getRowNumber() - 1 && newC >= lastRow;
    }

    @Override
    public int compareTo(Cell other) {
        if (cost < other.cost) return -1;
        if (cost > other.cost) return 1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Cell && index == ((Cell) o).index;
    }

    public int getValue() {
        return value;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Cell getPrevious() {
        return previous;
    }

    public void setPrevious(Cell previous) {
        this.previous = previous;
    }

    public ArrayList<Integer> getNeighbours() {
        return neighbours;
    }

    public int getIndex() {
        return index;
    }
}
