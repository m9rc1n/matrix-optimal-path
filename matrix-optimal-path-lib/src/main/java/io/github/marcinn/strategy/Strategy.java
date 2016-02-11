package io.github.marcinn.strategy;

import io.github.marcinn.Cell;

public interface Strategy {
    Cell[] compute(Cell[] matrix);
}
