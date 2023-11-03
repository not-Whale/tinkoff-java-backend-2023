package edu.project2.graph;

import edu.project2.maze.Cell;
import java.util.HashSet;
import java.util.Set;

public class CellVertex {
    private final Cell cell;

    private Mark mark;

    private final Set<CellVertex> edges;

    public CellVertex(Cell cell) {
        if (cell == null || !cell.type().equals(Cell.Type.PASSAGE)) {
            throw new IllegalArgumentException("Стены не могут быть вершинами!");
        }

        this.cell = cell;
        this.mark = Mark.WHITE;
        this.edges = new HashSet<>();
    }

    public Mark getMark() {
        return mark;
    }

    public Cell getCell() {
        return cell;
    }

    public Set<CellVertex> getEdges() {
        return edges;
    }

    public void addEdge(CellVertex vertex) {
        edges.add(vertex);
    }

    public void removeEdge(CellVertex vertex) {
        edges.remove(vertex);
    }

    public void markWhite() {
        mark(Mark.WHITE);
    }

    public void markGray() {
        mark(Mark.GRAY);
    }

    public void markBlack() {
        mark(Mark.BLACK);
    }

    public void markPath() {
        mark(Mark.PATH);
    }

    private void mark(Mark mark) {
        this.mark = mark;
    }
}
