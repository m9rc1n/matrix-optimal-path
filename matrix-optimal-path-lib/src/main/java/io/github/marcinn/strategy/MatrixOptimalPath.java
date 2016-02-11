package io.github.marcinn.strategy;

import io.github.marcinn.Cell;

public class MatrixOptimalPath {

    private Strategy strategy;

    public MatrixOptimalPath(Strategy strategy) {
        this.strategy = strategy;
    }

    public Cell[] execute(Cell[] matrix) {
        return strategy.compute(matrix);
    }
}
