package edu.project2.render;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.List;

public class SimpleRenderer implements Renderer {
    @Override
    public String render(Maze maze) {
        return render(maze, List.of());
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder stringBuilder = new StringBuilder();
        Cell[][] grid = maze.getGrid();
        int height = maze.getHeight();
        int width = maze.getWidth();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                stringBuilder.append(getSymbolByCoord(grid, i, j));
            }
            stringBuilder.append("\n");
        }

        for (Coordinate coord : path) {
            int position = coord.col() + coord.row() * (width + 1);
            stringBuilder.replace(position, position + 1, "•");
        }

        return stringBuilder.toString().replaceAll("•", "\033[034m•\033[0m");
    }

    private String getSymbolByCoord(Cell[][] grid, int i, int j) {
        if (isPassage(grid[i][j])) {
            return " ";
        }

        int height = grid.length;
        int width = grid[0].length;

        boolean hasLeft = j > 0 && isWall(grid[i][j - 1]);
        boolean hasRight = j < width - 1 && isWall(grid[i][j + 1]);
        boolean hasTop = i > 0 && isWall(grid[i - 1][j]);
        boolean hasBottom = i < height - 1 && isWall(grid[i + 1][j]);

        return getSymbolByFourNearby(hasLeft, hasRight, hasTop, hasBottom);
    }

    private String getSymbolByFourNearby(boolean hasLeft, boolean hasRight, boolean hasTop, boolean hasBottom) {
        if (hasLeft && hasRight && hasTop && hasBottom) {
            return  "┼";
        }
        return getSymbolByThreeNearby(hasLeft, hasRight, hasTop, hasBottom);
    }

    private String getSymbolByThreeNearby(boolean hasLeft, boolean hasRight, boolean hasTop, boolean hasBottom) {
        String symbol;
        if (hasLeft && hasRight && hasTop) {
            symbol = "┴";
        } else if (hasLeft && hasRight && hasBottom) {
            symbol = "┬";
        } else if (hasLeft && hasTop && hasBottom) {
            symbol = "┤";
        } else if (hasRight && hasTop && hasBottom) {
            symbol = "├";
        } else {
            symbol = getSymbolByTwoNearby(hasLeft, hasRight, hasTop, hasBottom);
        }
        return symbol;
    }

    private String getSymbolByTwoNearby(boolean hasLeft, boolean hasRight, boolean hasTop, boolean hasBottom) {
        String symbol;
        if (hasLeft && hasRight) {
            symbol = "─";
        } else if (hasLeft && hasTop) {
            symbol = "╯";
        } else if (hasLeft && hasBottom) {
            symbol = "╮";
        } else if (hasRight && hasTop) {
            symbol = "╰";
        } else if (hasRight && hasBottom) {
            symbol = "╭";
        } else if (hasTop && hasBottom) {
            symbol = "│";
        } else {
            symbol = getSymbolByOneNearby(hasLeft, hasRight, hasTop, hasBottom);
        }
        return symbol;
    }

    private String getSymbolByOneNearby(boolean hasLeft, boolean hasRight, boolean hasTop, boolean hasBottom) {
        String symbol;
        if (hasLeft) {
            symbol = "╴";
        } else if (hasRight) {
            symbol = "╶";
        } else if (hasTop) {
            symbol = "╵";
        } else if (hasBottom) {
            symbol = "╷";
        } else {
            symbol = null;
        }
        return symbol;
    }

    private boolean isWall(Cell cell) {
        return cell.type().equals(Cell.Type.WALL);
    }

    private boolean isPassage(Cell cell) {
        return cell.type().equals(Cell.Type.PASSAGE);
    }
}
