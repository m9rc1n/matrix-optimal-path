package io.github.marcinn.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CellCorrectDataTest {

    private final int value;
    private final int index;
    private final MatrixInfo info;
    private final int[] neighbours;

    public CellCorrectDataTest(int value, int index, MatrixInfo info, int[] neighbours) {
        this.value = value;
        this.index = index;
        this.info = info;
        this.neighbours = neighbours;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{100, 20, new MatrixInfo(100,
                5), new int[]{15, 16, 21, 25, 26}}, {100, 45, new MatrixInfo(133,
                7), new int[]{37, 38, 39, 44, 46, 51, 52, 53}}, {100, 0, new MatrixInfo(133,
                7), new int[]{1, 7, 8}}, {100, 132, new MatrixInfo(133,
                7), new int[]{124, 125, 131}}, {100, 19, new MatrixInfo(100,
                5), new int[]{13, 14, 18, 23, 24}}});
    }

    private int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i);
        }
        return ret;
    }

    @Test
    public void testCell() throws Exception {
        Cell c = new Cell(value, index, info);
        assertEquals(value, c.getValue());
        assertEquals(Integer.MAX_VALUE, c.getCost());
        assertEquals(null, c.getPrevious());
        assertArrayEquals(neighbours, convertIntegers(c.getNeighbours()));
    }
}
