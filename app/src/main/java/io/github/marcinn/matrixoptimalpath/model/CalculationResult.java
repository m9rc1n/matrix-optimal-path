package io.github.marcinn.matrixoptimalpath.model;

import android.util.SparseBooleanArray;

public class CalculationResult {

    private int columnNumber;
    private MatrixCell words[];
    private SparseBooleanArray path;

    public CalculationResult(int columnNumber, MatrixCell[] words, SparseBooleanArray path) {
        this.columnNumber = columnNumber;
        this.words = words;
        this.path = path;
    }

    protected CalculationResult() {
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public MatrixCell[] getWords() {
        return words;
    }

    public SparseBooleanArray getPath() {
        return path;
    }

    public int size() {
        return words.length;
    }

    public int getCostOfOptimalPath() {
        return words[size() - 1].getCost();
    }
}