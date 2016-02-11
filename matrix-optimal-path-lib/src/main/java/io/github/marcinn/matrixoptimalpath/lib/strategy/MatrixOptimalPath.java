package io.github.marcinn.matrixoptimalpath.lib.strategy;

import io.github.marcinn.matrixoptimalpath.lib.model.Cell;

public class MatrixOptimalPath {

    private Strategy strategy;

    public MatrixOptimalPath(Strategy strategy) {
        this.strategy = strategy;
    }

    public Cell[] execute(Cell[] matrix) {
        return strategy.compute(matrix);
    }
}
