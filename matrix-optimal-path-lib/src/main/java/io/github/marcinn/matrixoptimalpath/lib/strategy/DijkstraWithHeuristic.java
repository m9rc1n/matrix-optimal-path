package io.github.marcinn.matrixoptimalpath.lib.strategy;

import io.github.marcinn.matrixoptimalpath.lib.model.Cell;

public class DijkstraWithHeuristic extends Dijkstra {

    private final int maxDistanceBetweenCells;

    public DijkstraWithHeuristic(int maxDistanceBetweenCells) {
        this.maxDistanceBetweenCells = maxDistanceBetweenCells;
    }

    @Override
    public Cell[] compute(Cell[] matrix) {
        return super.compute(calculateHeuristicFun(matrix));
    }

    private Cell[] calculateHeuristicFun(Cell[] matrix) {
        for (Cell c : matrix) {
            if (c.getRow() < c.getColumn()) {
                c.setCost(c.getColumn() * maxDistanceBetweenCells);
            } else {
                c.setCost(c.getRow() * maxDistanceBetweenCells);
            }
        }
        return matrix;
    }
}
