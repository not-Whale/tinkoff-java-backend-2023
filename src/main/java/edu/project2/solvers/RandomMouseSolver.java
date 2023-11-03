package edu.project2.solvers;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMouseSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Random random = new Random();

        Cell startCell = maze.getCell(start.row() * 2 + 1, start.col() * 2 + 1);
        Cell endCell = maze.getCell(end.row() * 2 + 1, end.col() * 2 + 1);

        Cell currentCell = startCell;
        path.add(new Coordinate(startCell.row(), startCell.col()));
        while (!currentCell.equals(endCell)) {
            Cell[] moves = getNearbyMoves(maze, currentCell);
            currentCell = moves[random.nextInt(moves.length)];
            path.add(new Coordinate(currentCell.row(), currentCell.col()));
        }

        return path;
    }

    private Cell[] getNearbyMoves(Maze maze, Cell cell) {
        ArrayList<Cell> nearbyCells = new ArrayList<>();

        if (cell.row() > 0) {
            Cell bottomCell = maze.getCell(cell.row() - 1, cell.col());
            if (bottomCell.type().equals(Cell.Type.PASSAGE)) {
                nearbyCells.add(bottomCell);
            }
        }

        if (cell.row() < maze.getHeight() - 1) {
            Cell topCell = maze.getCell(cell.row() + 1, cell.col());
            if (topCell.type().equals(Cell.Type.PASSAGE)) {
                nearbyCells.add(topCell);
            }
        }

        if (cell.col() > 0) {
            Cell rightCell = maze.getCell(cell.row(), cell.col() - 1);
            if (rightCell.type().equals(Cell.Type.PASSAGE)) {
                nearbyCells.add(rightCell);
            }
        }

        if (cell.col() < maze.getWidth() - 1) {
            Cell leftCell = maze.getCell(cell.row(), cell.col() + 1);
            if (leftCell.type().equals(Cell.Type.PASSAGE)) {
                nearbyCells.add(leftCell);
            }
        }

        return nearbyCells.toArray(new Cell[] {});
    }
}
