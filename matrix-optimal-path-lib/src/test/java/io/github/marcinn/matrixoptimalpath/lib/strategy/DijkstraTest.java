package io.github.marcinn.matrixoptimalpath.lib.strategy;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import io.github.marcinn.matrixoptimalpath.lib.model.Cell;
import io.github.marcinn.matrixoptimalpath.lib.util.MatrixHelper;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class DijkstraTest {

    private static MatrixOptimalPath strategy;
    private final String text;
    private final int columnNumber;
    private final int cost;

    public DijkstraTest(String text, int columnNumber, int cost) {
        this.text = text;
        this.columnNumber = columnNumber;
        this.cost = cost;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{"a a a a a", 4, 0}, {"a b b b b a b b b a b b b a a a", 4, 0}, {"a a a a b", 5, 1}, {"a", 1, 0}});
    }

    @BeforeClass
    public static void setUp() {
        strategy = new MatrixOptimalPath(new Dijkstra());
    }

    @Test
    public void testMatrixInfoGenerationIsCorrect() throws Exception {
        MatrixHelper helper = new MatrixHelper();
        Cell[] matrix = helper.generateMatrixFromString(text.toLowerCase().split("\\s+"),
                columnNumber);
        Cell[] computedMatrix = strategy.execute(matrix);
        assertEquals(cost, computedMatrix[computedMatrix.length - 1].getCost());
    }
}
