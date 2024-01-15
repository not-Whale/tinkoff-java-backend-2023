package edu.project2.solvers;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Случайный выбор пути (random mouse algorithm).")
public class RandomMouseSolverTest {
    @Test
    @DisplayName("Поиск пути.")
    void randomMouseSolve() {
        // given
        RandomMouseSolver solver = new RandomMouseSolver(0);
        Maze maze = new Maze(3, 3);
        maze.deleteWall(1, 2);
        maze.deleteWall(1, 4);

        maze.deleteWall(2, 1);
        maze.deleteWall(2, 3);
        maze.deleteWall(2, 5);

        maze.deleteWall(4, 1);
        maze.deleteWall(4, 3);
        maze.deleteWall(4, 5);

        // when
        List<Coordinate> path = solver.solve(
            maze,
            new Coordinate(0, 0),
            new Coordinate(2, 2)
        );

        // then
        assertThat(path).isNotNull().containsExactly(
            new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(1, 3),
            new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4),
            new Coordinate(1, 3), new Coordinate(1, 4), new Coordinate(1, 3),
            new Coordinate(2, 3), new Coordinate(3, 3), new Coordinate(2, 3),
            new Coordinate(1, 3), new Coordinate(1, 4), new Coordinate(1, 5),
            new Coordinate(1, 4), new Coordinate(1, 5), new Coordinate(1, 4),
            new Coordinate(1, 3), new Coordinate(1, 4), new Coordinate(1, 3),
            new Coordinate(1, 2), new Coordinate(1, 1), new Coordinate(2, 1),
            new Coordinate(1, 1), new Coordinate(2, 1), new Coordinate(3, 1),
            new Coordinate(2, 1), new Coordinate(1, 1), new Coordinate(1, 2),
            new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(1, 3),
            new Coordinate(1, 4), new Coordinate(1, 3), new Coordinate(1, 4),
            new Coordinate(1, 3), new Coordinate(2, 3), new Coordinate(1, 3),
            new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4),
            new Coordinate(1, 5), new Coordinate(2, 5), new Coordinate(3, 5),
            new Coordinate(4, 5), new Coordinate(5, 5)
        );
    }

    @Test
    @DisplayName("Поиск пути. Null-лабиринт.")
    void randomMouseSolveNullMaze() {
        // given
        RandomMouseSolver solver = new RandomMouseSolver();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(1, 1);

        // then
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> solver.solve(
                null,
                start,
                end
            )
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Лабиринт не может быть null!");
    }

    @Test
    @DisplayName("Поиск пути. Null начальная координата.")
    void randomMouseSolveNullStartCoordinate() {
        // given
        RandomMouseSolver solver = new RandomMouseSolver();
        Maze maze = new Maze(3, 3);
        Coordinate end = new Coordinate(1, 1);

        // then
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> solver.solve(
                maze,
                null,
                end
            )
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Начальная координата не может быть нулевой!");
    }

    @Test
    @DisplayName("Поиск пути. Null конечная координата.")
    void randomMouseSolveNullEndCoordinate() {
        // given
        RandomMouseSolver solver = new RandomMouseSolver();
        Maze maze = new Maze(3, 3);
        Coordinate start = new Coordinate(0, 0);

        // then
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> solver.solve(
                maze,
                start,
                null
            )
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Конечная координата не может быть нулевой!");
    }
}
