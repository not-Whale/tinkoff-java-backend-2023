package edu.project2.solvers;

import edu.project2.graph.CellVertex;
import edu.project2.graph.MazeGraph;
import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMouseGraphSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Random random = new Random();
        MazeGraph graph = new MazeGraph(maze);

        Cell startCell = maze.getCell(start.row() * 2 + 1, start.col() * 2 + 1);
        Cell endCell = maze.getCell(end.row() * 2 + 1, end.col() * 2 + 1);

        CellVertex currentVertex = graph.getVertexByCell(startCell);
        while (!currentVertex.getCell().equals(endCell)) {
            path.add(new Coordinate(currentVertex.getCell().row(), currentVertex.getCell().col()));
            List<CellVertex> edges = currentVertex.getEdges().stream().toList();
            currentVertex = edges.get(random.nextInt(edges.size()));
        }

        return path;
    }
}
