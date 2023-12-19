package edu.project2.generators;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class KruskalGenerator implements Generator {
    private final Long seed;

    public KruskalGenerator() {
        this.seed = null;
    }

    public KruskalGenerator(long seed) {
        this.seed = seed;
    }

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        ArrayList<Cell> walls = new ArrayList<>(List.of(maze.getWalls()));

        if (seed == null) {
            Collections.shuffle(walls);
        } else {
            Random random = new Random(seed);
            Collections.shuffle(walls, random);
        }

        ArrayList<HashSet<Cell>> cellsSet = new ArrayList<>();
        for (Cell cell : maze.getPassages()) {
            cellsSet.add(new HashSet<>() {{
                add(cell);
            }});
        }

        for (Cell wall : walls) {
            Cell[] cellsDividedByWall = cellsDividedBy(maze, wall);
            if (cellsDividedByWall != null) {
                HashSet<Cell> firstSet = findSetByElem(cellsSet, cellsDividedByWall[0]);
                HashSet<Cell> secondSet = findSetByElem(cellsSet, cellsDividedByWall[1]);
                if (!firstSet.equals(secondSet)) {
                    maze.deleteWall(wall.row(), wall.col());
                    firstSet.addAll(secondSet);
                    cellsSet.remove(secondSet);
                }
            }
        }

        return maze;
    }

    private HashSet<Cell> findSetByElem(ArrayList<HashSet<Cell>> cellsSet, Cell cell) {
        for (HashSet<Cell> set : cellsSet) {
            if (set.contains(cell)) {
                return set;
            }
        }
        return null;
    }

    private Cell[] cellsDividedBy(Maze maze, Cell wall) {
        if (wall.row() > 1 && wall.row() < maze.getHeight() - 1) {
            Cell topCell = maze.getCell(wall.row() - 1, wall.col());
            Cell bottomCell = maze.getCell(wall.row() + 1, wall.col());
            if (topCell.type().equals(Cell.Type.PASSAGE) && bottomCell.type().equals(Cell.Type.PASSAGE)) {
                return new Cell[] {topCell, bottomCell};
            }
        }
        if (wall.col() > 1 && wall.col() < maze.getWidth() - 1) {
            Cell leftCell = maze.getCell(wall.row(), wall.col() - 1);
            Cell rightCell = maze.getCell(wall.row(), wall.col() + 1);
            if (leftCell.type().equals(Cell.Type.PASSAGE) && rightCell.type().equals(Cell.Type.PASSAGE)) {
                return new Cell[] {leftCell, rightCell};
            }
        }
        return null;
    }
}
