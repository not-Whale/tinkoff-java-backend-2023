package edu.project2.utils;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;

public class MazeUtils {
    private MazeUtils() {}

    public static Cell[] getMovesWithWhitelist(Maze maze, Cell cell, int delta, List<Cell> whitelist) {
        ArrayList<Cell> nearbyCells = new ArrayList<>();

        if (cell.row() > delta - 1) {
            Cell bottomCell = maze.getCell(cell.row() - delta, cell.col());
            if (whitelist.contains(bottomCell)) {
                nearbyCells.add(bottomCell);
            }
        }

        if (cell.row() < maze.getHeight() - delta) {
            Cell topCell = maze.getCell(cell.row() + delta, cell.col());
            if (whitelist.contains(topCell)) {
                nearbyCells.add(topCell);
            }
        }

        if (cell.col() > delta - 1) {
            Cell rightCell = maze.getCell(cell.row(), cell.col() - delta);
            if (whitelist.contains(rightCell)) {
                nearbyCells.add(rightCell);
            }
        }

        if (cell.col() < maze.getWidth() - delta) {
            Cell leftCell = maze.getCell(cell.row(), cell.col() + delta);
            if (whitelist.contains(leftCell)) {
                nearbyCells.add(leftCell);
            }
        }

        return nearbyCells.toArray(new Cell[] {});
    }
}
