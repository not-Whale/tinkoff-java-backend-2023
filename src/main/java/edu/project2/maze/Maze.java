package edu.project2.maze;

import java.util.ArrayList;

public final class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        if (height < 1 || width < 1) {
            throw new IllegalArgumentException("Измерения лабиринта должны быть положительными!");
        }

        this.height = height * 2 + 1;
        this.width = width * 2 + 1;
        this.grid = new Cell[this.height][this.width];
        initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < height; i++) {
            grid[i] = new Cell[width];
            for (int j = 0; j < width; j++) {
                if (i == height - 1 || j == width - 1 || i % 2 == 0 || j % 2 == 0) {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                }
            }
        }
    }

    public void deleteWall(int i, int j) {
        if (i < 0 || i > height - 1 || j < 0 || j > width - 1) {
            throw new IllegalArgumentException("Такой ячейки не существует!");
        }

        setCell(i, j, Cell.Type.PASSAGE);
    }

    private void setCell(int i, int j, Cell.Type type) {
        grid[i][j] = new Cell(i, j, type);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell[] getPassages() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell currentCell = grid[i][j];
                if (currentCell.type().equals(Cell.Type.PASSAGE)) {
                    cells.add(currentCell);
                }
            }
        }
        return cells.toArray(new Cell[]{});
    }

    public Cell[] getWalls() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 1; i < height; i += 2) {
            for (int j = 2; j < width - 1; j += 2) {
                Cell currentCell = grid[i][j];
                if (currentCell.type().equals(Cell.Type.WALL)) {
                    cells.add(currentCell);
                }
            }
        }
        for (int i = 2; i < height - 1; i += 2) {
            for (int j = 1; j < width; j += 2) {
                Cell currentCell = grid[i][j];
                if (currentCell.type().equals(Cell.Type.WALL)) {
                    cells.add(currentCell);
                }
            }
        }
        return cells.toArray(new Cell[]{});
    }

    public Cell getCell(int i, int j) {
        if (i < 0 || i > height - 1 || j < 0 || j > width - 1) {
            throw new IllegalArgumentException("Такой ячейки не существует!");
        }

        return grid[i][j];
    }

    public Cell[][] getGrid() {
        return grid;
    }
}
