package io.github.marcinn.matrixoptimalpath.lib.strategy;

import io.github.marcinn.matrixoptimalpath.lib.model.Cell;

public interface Strategy {
    Cell[] compute(Cell[] matrix);
}
