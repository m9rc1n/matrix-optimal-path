package io.github.marcinn;

public class MatrixInfo {

    private final int columnNumber;
    private final int rowNumber;
    private final int lastRowColumnNumber;
    private final int length;

    public MatrixInfo(int length, int columnNumber) {
        if (length <= 0) throw new RuntimeException("Matrix should has length bigger than 0");
        if (columnNumber <= 0) throw new RuntimeException("Column number should be bigger than 0");
        this.columnNumber = columnNumber;
        this.length = length;
        this.rowNumber = (int) Math.ceil((double) length / columnNumber);
        this.lastRowColumnNumber = length - (length / columnNumber) * columnNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getLastRowColumnNumber() {
        return lastRowColumnNumber;
    }

    public int getLength() {
        return length;
    }
}
