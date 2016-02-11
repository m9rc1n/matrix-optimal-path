package io.github.marcinn.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MatrixInfoCorrectDataTest {
    private final int length;
    private final int columnNumber;
    private final int rowNumber;
    private final int lastRowColumnNumber;

    public MatrixInfoCorrectDataTest(int length, int columnNumber, int rowNumber, int lastRowNr) {
        this.length = length;
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
        this.lastRowColumnNumber = lastRowNr;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{100, 10, 10, 0}, {13, 12, 2, 1}, {100, 13, 8, 9}});
    }

    @Test
    public void testMatrixInfoGenerationIsCorrect() throws Exception {
        MatrixInfo info = new MatrixInfo(length, columnNumber);
        assertEquals(length, info.getLength());
        assertEquals(columnNumber, info.getColumnNumber());
        assertEquals(rowNumber, info.getRowNumber());
        assertEquals(lastRowColumnNumber, info.getLastRowColumnNumber());
    }
}
