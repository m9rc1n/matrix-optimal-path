package io.github.marcinn.matrixoptimalpath.model;

public class CalculationResultError extends CalculationResult {

    private int messageResId;

    public CalculationResultError(int messageResId) {
        this.messageResId = messageResId;
    }

    public int getMessageResId() {
        return messageResId;
    }
}