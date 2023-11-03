package edu.project2.graph;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class MazeGraph {
    private final Set<CellVertex> vertexSet;

    private final int height;

    private final int width;

    public MazeGraph(Maze maze) {
        height = maze.getHeight();
        width = maze.getWidth();
        vertexSet = new HashSet<>();
        createVertexesAndEdges(maze);
    }

    private void createVertexesAndEdges(Maze maze) {
        Cell[] cells = maze.getPassages();
        for (int i = 0; i < cells.length; i++) {
            CellVertex vertexI = new CellVertex(cells[i]);
            addVertex(vertexI);
            for (int j = i + 1; j < cells.length; j++) {
                if (isNearby(cells[i], cells[j])) {
                    CellVertex vertexJ = new CellVertex(cells[j]);
                    addVertex(vertexJ);
                    addEdge(vertexI, vertexJ);
                }
            }
        }
    }

    private boolean isNearby(Cell first, Cell second) {
        return ((Math.abs(first.col() - second.col()) == 1 && first.row() == second.row())
            || (Math.abs(first.row() - second.row()) == 1 && first.col() == second.col()));
    }

    public Maze toMaze() {
        Maze maze = new Maze((height - 1) / 2, (width - 1) / 2);

        for (CellVertex vertex : vertexSet) {
            maze.deleteWall(vertex.getCell().col(), vertex.getCell().row());
        }

        return maze;
    }

    public Set<CellVertex> getVertexSet() {
        return vertexSet;
    }

    public CellVertex getVertexByCell(Cell cell) {
        for (CellVertex vertex : vertexSet) {
            if (vertex.getCell().equals(cell)) {
                return vertex;
            }
        }
        return null;
    }

    public void addVertex(CellVertex vertex) {
        vertexSet.add(vertex);
    }

    public boolean addEdge(CellVertex first, CellVertex second) {
        if (!vertexSet.contains(first) || !vertexSet.contains(second)) {
            throw new IllegalArgumentException("Такое ребро не может существовать!");
        }

        first.addEdge(second);
        second.addEdge(first);
        return true;
    }

    public void removeEdge(CellVertex first, CellVertex second) {
        if (!vertexSet.contains(first) || !vertexSet.contains(second)) {
            throw new IllegalArgumentException("Такого ребра не существует!");
        }

        first.removeEdge(second);
        second.removeEdge(first);
    }
}
