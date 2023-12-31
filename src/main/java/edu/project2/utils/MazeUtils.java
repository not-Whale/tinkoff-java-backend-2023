package edu.project2.utils;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;

public class MazeUtils {
    private MazeUtils() {}

    public static Cell[] getMovesWithWhitelist(Maze maze, Cell cell, int delta, List<Cell> whitelist) {
        if (maze == null) {
            throw new IllegalArgumentException("Лабиринт не может быть null!");
        }

        if (cell == null) {
            throw new IllegalArgumentException("Ячейка не может быть null!");
        }

        if (delta < 1) {
            throw new IllegalArgumentException("Дельта должна быть положительной!");
        }

        if (whitelist == null) {
            throw new IllegalArgumentException("Белый список не может быть null!");
        }

        ArrayList<Cell> nearbyCells = new ArrayList<>();

        if (cell.row() > delta - 1) {
            Cell topCell = maze.getCell(cell.row() - delta, cell.col());
            if (whitelist.contains(topCell)) {
                nearbyCells.add(topCell);
            }
        }

        if (cell.row() < maze.getHeight() - delta) {
            Cell bottomCell = maze.getCell(cell.row() + delta, cell.col());
            if (whitelist.contains(bottomCell)) {
                nearbyCells.add(bottomCell);
            }
        }

        if (cell.col() > delta - 1) {
            Cell leftCell = maze.getCell(cell.row(), cell.col() - delta);
            if (whitelist.contains(leftCell)) {
                nearbyCells.add(leftCell);
            }
        }

        if (cell.col() < maze.getWidth() - delta) {
            Cell rightCell = maze.getCell(cell.row(), cell.col() + delta);
            if (whitelist.contains(rightCell)) {
                nearbyCells.add(rightCell);
            }
        }

        return nearbyCells.toArray(new Cell[] {});
    }
}
