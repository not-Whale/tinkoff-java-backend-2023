package edu.project2;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.solvers.DepthFirstSearchParallelSolver;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Решение лабиринта многопоточно.")
public class ParallelSolverTest {
    @Test
    @DisplayName("Поиск пути.")
    void dfsSolve() {
        // given
        DepthFirstSearchParallelSolver depthFirstSearchParallelSolver = new DepthFirstSearchParallelSolver();
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
        List<Coordinate> path = depthFirstSearchParallelSolver.solve(
            maze,
            new Coordinate(0, 0),
            new Coordinate(2, 2)
        );

        // then
        assertThat(path).isNotNull().containsOnly(
            new Coordinate(5, 5),
            new Coordinate(4, 5),
            new Coordinate(3, 5),
            new Coordinate(2, 5),
            new Coordinate(1, 5),
            new Coordinate(1, 4),
            new Coordinate(1, 3),
            new Coordinate(1, 2),
            new Coordinate(1, 1)
        );
    }

    @Test
    @DisplayName("Поиск пути. Null-лабиринт.")
    void dfsSolveNullMaze() {
        // given
        DepthFirstSearchParallelSolver depthFirstSearchParallelSolver = new DepthFirstSearchParallelSolver();
        Maze maze = null;
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(1, 1);

        // then
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> depthFirstSearchParallelSolver.solve(
                maze,
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
    void dfsSolveNullStartCoord() {
        // given
        DepthFirstSearchParallelSolver depthFirstSearchParallelSolver = new DepthFirstSearchParallelSolver();
        Maze maze = new Maze(3, 3);
        Coordinate start = null;
        Coordinate end = new Coordinate(1, 1);

        // then
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> depthFirstSearchParallelSolver.solve(
                maze,
                start,
                end
            )
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Начальная координата не может быть нулевой!");
    }

    @Test
    @DisplayName("Поиск пути. Null конечная координата.")
    void dfsSolveNullEndCoord() {
        // given
        DepthFirstSearchParallelSolver depthFirstSearchParallelSolver = new DepthFirstSearchParallelSolver();
        Maze maze = new Maze(3, 3);
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = null;

        // then
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> depthFirstSearchParallelSolver.solve(
                maze,
                start,
                end
            )
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Конечная координата не может быть нулевой!");
    }
}
