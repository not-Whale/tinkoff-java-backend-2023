package edu.project2.solvers;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearchSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
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
                nearby = getNearbyMoves(maze, currentCell, remained);
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

    private Cell[] getNearbyMoves(Maze maze, Cell cell, ArrayList<Cell> remained) {
        ArrayList<Cell> nearbyCells = new ArrayList<>();

        if (cell.row() > 0) {
            Cell bottomCell = maze.getCell(cell.row() - 1, cell.col());
            if (remained.contains(bottomCell)) {
                nearbyCells.add(bottomCell);
            }
        }

        if (cell.row() < maze.getHeight() - 1) {
            Cell topCell = maze.getCell(cell.row() + 1, cell.col());
            if (remained.contains(topCell)) {
                nearbyCells.add(topCell);
            }
        }

        if (cell.col() > 0) {
            Cell rightCell = maze.getCell(cell.row(), cell.col() - 1);
            if (remained.contains(rightCell)) {
                nearbyCells.add(rightCell);
            }
        }

        if (cell.col() < maze.getWidth() - 1) {
            Cell leftCell = maze.getCell(cell.row(), cell.col() + 1);
            if (remained.contains(leftCell)) {
                nearbyCells.add(leftCell);
            }
        }

        return nearbyCells.toArray(new Cell[] {});
    }
}
