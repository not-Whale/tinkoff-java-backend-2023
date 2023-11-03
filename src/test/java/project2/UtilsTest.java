package project2;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import edu.project2.utils.MazeUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Утилиты для работы с лабиринтом.")
public class UtilsTest {
    @Test
    @DisplayName("Получение соседних ячеек, содержащихся в белом листе.")
    void getMovesWithWhitelist() {
        // given
        Maze maze = new Maze(5, 10);
        Cell cell = maze.getCell(3, 3);
        int delta = 2;
        Cell[] passages = maze.getPassages();

        // when
        Cell[] nearbyMoves = MazeUtils.getMovesWithWhitelist(maze, cell, delta, Arrays.stream(passages).toList());

        // then
        assertThat(nearbyMoves).isNotNull().containsExactly(
            maze.getCell(1, 3),
            maze.getCell(5, 3),
            maze.getCell(3, 1),
            maze.getCell(3, 5)
        );
    }

    @Test
    @DisplayName("Получение соседних ячеек, содержащихся в белом листе. Null-лабиринт.")
    void getMovesWithWhitelistNullMaze() {
        // given
        Maze maze = null;
        int delta = 2;
        Cell cell = new Cell(1, 1, Cell.Type.PASSAGE);
        List<Cell> passages = List.of(cell);

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> MazeUtils.getMovesWithWhitelist(maze, cell, delta, passages)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Лабиринт не может быть null!");
    }

    @Test
    @DisplayName("Получение соседних ячеек, содержащихся в белом листе. Null-ячейка.")
    void getMovesWithWhitelistNullCell() {
        // given
        Maze maze = new Maze(5, 10);
        Cell cell = null;
        int delta = 2;
        Cell[] passages = maze.getPassages();

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> MazeUtils.getMovesWithWhitelist(maze, cell, delta, Arrays.stream(passages).toList())
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Ячейка не может быть null!");
    }

    @Test
    @DisplayName("Получение соседних ячеек, содержащихся в белом листе. Null-список.")
    void getMovesWithWhitelistNullWhitelist() {
        // given
        Maze maze = new Maze(5, 10);
        Cell cell = maze.getCell(1, 1);
        int delta = 2;
        ArrayList<Cell> passages = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> MazeUtils.getMovesWithWhitelist(maze, cell, delta, passages)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Белый список не может быть null!");
    }

    @Test
    @DisplayName("Получение соседних ячеек, содержащихся в белом листе. Неположительная дельта.")
    void getMovesWithWhitelistNonPositiveDelta() {
        // given
        Maze maze = new Maze(5, 10);
        Cell cell = maze.getCell(1, 1);
        int delta = 0;
        Cell[] passages = maze.getPassages();

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> MazeUtils.getMovesWithWhitelist(maze, cell, delta, Arrays.stream(passages).toList())
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Дельта должна быть положительной!");
    }
}
