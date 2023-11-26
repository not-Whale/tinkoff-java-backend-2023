package edu.project2;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Лабиринт, ячейки и координаты.")
public class MazeTest {
    @Test
    @DisplayName("Лабиринт. Инициализация измерений.")
    void mazeDimensions() {
        // given
        int height = 5;
        int width = 10;

        // when
        Maze maze = new Maze(height, width);

        // then
        assertThat(maze.getHeight()).isEqualTo(11);
        assertThat(maze.getWidth()).isEqualTo(21);
    }

    @Test
    @DisplayName("Лабиринт. Инициализация измерений. Неположительная длина.")
    void mazeNonPositiveWidth() {
        // given
        int height = 5;
        int width = -10;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Maze(height, width)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Измерения лабиринта должны быть положительными!");
    }

    @Test
    @DisplayName("Лабиринт. Инициализация измерений. Неположительная высота.")
    void mazeNonPositiveHeight() {
        // given
        int height = -5;
        int width = 10;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Maze(height, width)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Измерения лабиринта должны быть положительными!");
    }

    @Test
    @DisplayName("Лабиринт. Инициализация поля.")
    void mazeGridCreation() {
        // given
        int height = 2;
        int width = 2;
        Cell[][] grid = new Cell[][] {
            new Cell[] {
                new Cell(0, 0, Cell.Type.WALL),
                new Cell(0, 1, Cell.Type.WALL),
                new Cell(0, 2, Cell.Type.WALL),
                new Cell(0, 3, Cell.Type.WALL),
                new Cell(0, 4, Cell.Type.WALL),
            },
            new Cell[] {
                new Cell(1, 0, Cell.Type.WALL),
                new Cell(1, 1, Cell.Type.PASSAGE),
                new Cell(1, 2, Cell.Type.WALL),
                new Cell(1, 3, Cell.Type.PASSAGE),
                new Cell(1, 4, Cell.Type.WALL),
            },
            new Cell[] {
                new Cell(2, 0, Cell.Type.WALL),
                new Cell(2, 1, Cell.Type.WALL),
                new Cell(2, 2, Cell.Type.WALL),
                new Cell(2, 3, Cell.Type.WALL),
                new Cell(2, 4, Cell.Type.WALL),
            },
            new Cell[] {
                new Cell(3, 0, Cell.Type.WALL),
                new Cell(3, 1, Cell.Type.PASSAGE),
                new Cell(3, 2, Cell.Type.WALL),
                new Cell(3, 3, Cell.Type.PASSAGE),
                new Cell(3, 4, Cell.Type.WALL),
            },
            new Cell[] {
                new Cell(4, 0, Cell.Type.WALL),
                new Cell(4, 1, Cell.Type.WALL),
                new Cell(4, 2, Cell.Type.WALL),
                new Cell(4, 3, Cell.Type.WALL),
                new Cell(4, 4, Cell.Type.WALL),
            },
        };

        // when
        Maze maze = new Maze(height, width);

        // then
        assertThat(maze.getGrid()).isNotNull().isEqualTo(grid);
    }

    @Test
    @DisplayName("Лабиринт. Удаление стены.")
    void mazeDeleteWall() {
        // given
        int height = 5;
        int width = 10;
        Maze maze = new Maze(height, width);

        // when
        assertThat(maze.getCell(2, 1)).isNotNull();
        assertThat(maze.getCell(2, 1).type()).isEqualTo(Cell.Type.WALL);
        maze.deleteWall(2, 1);

        // then
        assertThat(maze.getCell(2, 1)).isNotNull();
        assertThat(maze.getCell(2, 1).type()).isEqualTo(Cell.Type.PASSAGE);
    }

    @Test
    @DisplayName("Лабиринт. Удаление стены. Высота за пределами поля.")
    void mazeDeleteWallIncorrectHeight() {
        // given
        int height = 2;
        int width = 2;
        Maze maze = new Maze(height, width);

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> maze.deleteWall(100, 1)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Такой ячейки не существует!");
    }

    @Test
    @DisplayName("Лабиринт. Удаление стены. Ширина за пределами поля.")
    void mazeDeleteWallIncorrectWidth() {
        // given
        int height = 2;
        int width = 2;
        Maze maze = new Maze(height, width);

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> maze.deleteWall(1, 100)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Такой ячейки не существует!");
    }

    @Test
    @DisplayName("Лабиринт. Получение ячеек.")
    void mazeGetPassages() {
        // given
        int height = 2;
        int width = 2;
        Cell[] passages = new Cell[] {
            new Cell(1, 1, Cell.Type.PASSAGE),
            new Cell(1, 3, Cell.Type.PASSAGE),
            new Cell(3, 1, Cell.Type.PASSAGE),
            new Cell(3, 3, Cell.Type.PASSAGE)
        };

        // when
        Maze maze = new Maze(height, width);

        // then
        assertThat(maze.getPassages()).isNotNull().containsExactly(passages);
    }

    @Test
    @DisplayName("Лабиринт. Получение стен.")
    void mazeGetWalls() {
        // given
        int height = 2;
        int width = 2;
        Cell[] walls = new Cell[] {
            new Cell(1, 2, Cell.Type.WALL),
            new Cell(3, 2, Cell.Type.WALL),
            new Cell(2, 1, Cell.Type.WALL),
            new Cell(2, 3, Cell.Type.WALL)
        };

        // when
        Maze maze = new Maze(height, width);

        // then
        assertThat(maze.getWalls()).isNotNull().containsExactly(walls);
    }

    @Test
    @DisplayName("Лабиринт. Получение ячейки.")
    void mazeGetCell() {
        // given
        int height = 2;
        int width = 2;
        Cell cell = new Cell(1, 1, Cell.Type.PASSAGE);

        // when
        Maze maze = new Maze(height, width);

        // then
        assertThat(maze.getCell(1, 1)).isNotNull().isEqualTo(cell);
    }

    @Test
    @DisplayName("Лабиринт. Получение ячейки. Высота за пределами поля.")
    void mazeGetCellIncorrectHeight() {
        // given
        int height = 2;
        int width = 2;
        Maze maze = new Maze(height, width);

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> maze.getCell(100, 1)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Такой ячейки не существует!");
    }

    @Test
    @DisplayName("Лабиринт. Получение ячейки. Ширина за пределами поля.")
    void mazeGetCellIncorrectWidth() {
        // given
        int height = 2;
        int width = 2;
        Maze maze = new Maze(height, width);

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> maze.getCell(1, 100)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Такой ячейки не существует!");
    }
}
