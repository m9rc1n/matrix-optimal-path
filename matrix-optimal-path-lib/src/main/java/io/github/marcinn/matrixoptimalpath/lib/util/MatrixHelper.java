package io.github.marcinn.matrixoptimalpath.lib.util;

import io.github.marcinn.matrixoptimalpath.lib.model.Cell;
import io.github.marcinn.matrixoptimalpath.lib.model.MatrixInfo;

public class MatrixHelper {

    public Cell[] generateMatrixFromString(String[] words, int columnNr) {
        if (words[0].length() == 0) {
            throw new IllegalArgumentException("First cell should not be empty");
        }
        if (columnNr <= 0) {
            throw new IllegalArgumentException("Column number should be bigger than zero");
        }
        Cell[] cells = new Cell[words.length];
        for (int i = 0; i < words.length; i++) {
            cells[i] = new Cell(words[i].codePointAt(0), i, new MatrixInfo(words.length, columnNr));
        }
        return cells;
    }
}
