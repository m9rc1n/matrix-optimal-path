package io.github.marcinn.strategy;

import java.util.PriorityQueue;
import java.util.Queue;

import io.github.marcinn.Cell;

public class Dijkstra implements Strategy {

    @Override
    public Cell[] compute(Cell[] matrix) {
        Queue<Cell> q = new PriorityQueue<>();
        initializeSourceCell(matrix[0], q);
        Cell u;
        while (!q.isEmpty()) {
            u = q.poll();
            if (isLastCell(matrix, u)) return matrix;
            if (isCostOfCellInfinity(u)) break;
            for (Integer destI : u.getNeighbours()) {
                Cell dest = matrix[destI];
                int newDist = u.getCost() + Math.abs(u.getValue() - dest.getValue());
                if (newDist < dest.getCost()) {
                    q.remove(dest);
                    dest.setCost(newDist);
                    dest.setPrevious(u);
                    q.add(dest);
                }
            }
        }
        return matrix;
    }

    private boolean isCostOfCellInfinity(Cell u) {
        return u.getCost() == Integer.MAX_VALUE;
    }

    private boolean isLastCell(Cell[] matrix, Cell cell) {
        return matrix[matrix.length - 1].equals(cell);
    }

    private void initializeSourceCell(Cell cell, Queue<Cell> q) {
        cell.setCost(0);
        q.add(cell);
    }
}
