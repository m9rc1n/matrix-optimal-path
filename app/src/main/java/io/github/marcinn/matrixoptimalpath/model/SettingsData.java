package io.github.marcinn.matrixoptimalpath.model;

public class SettingsData {
    private int columnNumber;
    private String text;

    public SettingsData(String text, int columnNumber) {
        this.text = text;
        this.columnNumber = columnNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }
}
