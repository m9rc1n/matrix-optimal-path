package io.github.marcinn.strategy;

import io.github.marcinn.model.Cell;

public interface Strategy {
    Cell[] compute(Cell[] matrix);
}
