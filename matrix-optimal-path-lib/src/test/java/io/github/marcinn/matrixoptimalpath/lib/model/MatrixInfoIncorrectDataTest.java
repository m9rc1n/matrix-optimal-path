package io.github.marcinn.matrixoptimalpath.lib.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MatrixInfoIncorrectDataTest {

    private final int columnNumber;
    private final int length;

    public MatrixInfoIncorrectDataTest(int length, int columnNumber) {
        this.length = length;
        this.columnNumber = columnNumber;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{-1, -1}, {0, 1}, {0, 0}, {-1, 1}, {1, -1}, {1, 0}});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMatrixInfoGenerationIncorrectParameters() throws Exception {
        new MatrixInfo(length, columnNumber);
    }

}
