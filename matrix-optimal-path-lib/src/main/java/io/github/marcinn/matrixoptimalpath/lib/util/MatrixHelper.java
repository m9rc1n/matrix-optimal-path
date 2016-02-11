package io.github.marcinn.matrixoptimalpath.lib.util;

import io.github.marcinn.matrixoptimalpath.lib.model.Cell;
import io.github.marcinn.matrixoptimalpath.lib.model.MatrixInfo;

public class MatrixHelper {

    public Cell[] generateMatrixFromString(String text, int columnNr) {
        String[] words = text.toLowerCase().split("\\s+");
        Cell[] cells = new Cell[words.length];
        for (int i = 0; i < words.length; i++) {
            cells[i] = new Cell(words[i].codePointAt(0), i, new MatrixInfo(words.length, columnNr));
        }
        return cells;
    }
}
