package io.github.marcinn.util;

import io.github.marcinn.Cell;
import io.github.marcinn.MatrixInfo;

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
