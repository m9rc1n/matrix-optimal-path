package io.github.marcinn.matrixoptimalpath.lib.strategy;

import io.github.marcinn.matrixoptimalpath.lib.model.Cell;

public class AStar extends Dijkstra {

    private final int maxDistanceBetweenCells;

    public AStar(int maxDistanceBetweenCells) {
        this.maxDistanceBetweenCells = maxDistanceBetweenCells;
    }

    @Override
    public Cell[] compute(Cell[] matrix) {
        return super.compute(calculateHeuristicFun(matrix));
    }

    private Cell[] calculateHeuristicFun(Cell[] matrix) {
        for (Cell c : matrix) {
            if (c.getRow() < c.getColumn()) {
                c.setCost(c.getColumn() * maxDistanceBetweenCells + 1);
            } else {
                c.setCost(c.getRow() * maxDistanceBetweenCells + 1);
            }
        }
        return matrix;
    }
}
