package edu.project2.solvers;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.utils.MazeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearchSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        if (maze == null) {
            throw new IllegalArgumentException("Лабиринт не может быть null!");
        }

        if (start == null) {
            throw new IllegalArgumentException("Начальная координата не может быть нулевой!");
        }

        if (end == null) {
            throw new IllegalArgumentException("Конечная координата не может быть нулевой!");
        }

        List<Coordinate> path = new ArrayList<>();
        Stack<Cell> stack = new Stack<>();
        ArrayList<Cell> remained = new ArrayList<>(List.of(maze.getPassages()));

        Cell startCell = maze.getCell(start.row() * 2 + 1, start.col() * 2 + 1);
        Cell endCell = maze.getCell(end.row() * 2 + 1, end.col() * 2 + 1);

        Cell currentCell = startCell;
        stack.push(currentCell);
        remained.remove(currentCell);
        while (currentCell.col() != endCell.col() || currentCell.row() != endCell.row()) {
            Cell[] nearby;
            do {
                currentCell = stack.pop();
                nearby = MazeUtils.getMovesWithWhitelist(maze, currentCell, 1, remained);
            } while (nearby.length == 0);
            stack.push(currentCell);

            currentCell = nearby[0];
            stack.push(currentCell);
            remained.remove(currentCell);
        }

        while (!stack.empty()) {
            Cell tmp = stack.pop();
            path.add(new Coordinate(tmp.row(), tmp.col()));
        }

        return path;
    }
}
