package io.github.marcinn.matrixoptimalpath.lib.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CellOperationTest {

    @Test
    public void testCell() throws Exception {
        Cell a = new Cell(100, 21, new MatrixInfo(100, 5));
        Cell b = new Cell(100, 21, new MatrixInfo(100, 5));
        assertTrue(a.equals(b));
        a.setCost(0);
        b.setCost(1);
        assertTrue(a.compareTo(b) == -1);
        a.setCost(1);
        b.setCost(1);
        assertTrue(a.compareTo(b) == 0);
        a.setCost(1);
        b.setCost(0);
        assertTrue(a.compareTo(b) == 1);
        a.setPrevious(b);
        assertTrue(a.getPrevious().equals(b));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIndexBiggerThanLength() {
        Cell a = new Cell(0, 20, new MatrixInfo(10, 5));
    }
}
