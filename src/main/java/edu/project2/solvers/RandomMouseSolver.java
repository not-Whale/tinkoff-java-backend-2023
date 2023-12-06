package edu.project2.solvers;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.utils.MazeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMouseSolver implements Solver {
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
        Random random = new Random();
        Cell[] passages = maze.getPassages();

        Cell startCell = maze.getCell(start.row() * 2 + 1, start.col() * 2 + 1);
        Cell endCell = maze.getCell(end.row() * 2 + 1, end.col() * 2 + 1);

        Cell currentCell = startCell;
        path.add(new Coordinate(startCell.row(), startCell.col()));
        while (!currentCell.equals(endCell)) {
            Cell[] moves = MazeUtils.getMovesWithWhitelist(maze, currentCell, 1, List.of(passages));
            currentCell = moves[random.nextInt(moves.length)];
            path.add(new Coordinate(currentCell.row(), currentCell.col()));
        }

        return path;
    }
}
