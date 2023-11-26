package edu.project2;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.solvers.DepthFirstSearchRecSolver;
import edu.project2.solvers.DepthFirstSearchSolver;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Решение лабиринта.")
public class SolversTest {
    @Nested
    @DisplayName("Поиск пути поиском в глубину.")
    class DFSSolver {
        @Test
        @DisplayName("Поиск пути.")
        void dfsSolve() {
            // given
            DepthFirstSearchSolver depthFirstSearchSolver = new DepthFirstSearchSolver();
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
            List<Coordinate> path = depthFirstSearchSolver.solve(
                maze,
                new Coordinate(0, 0),
                new Coordinate(2, 2)
            );

            // then
            assertThat(path).isNotNull().containsExactly(
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
            DepthFirstSearchSolver depthFirstSearchSolver = new DepthFirstSearchSolver();
            Maze maze = null;
            Coordinate start = new Coordinate(0, 0);
            Coordinate end = new Coordinate(1, 1);

            // then
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> depthFirstSearchSolver.solve(
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
            DepthFirstSearchSolver depthFirstSearchSolver = new DepthFirstSearchSolver();
            Maze maze = new Maze(3, 3);
            Coordinate start = null;
            Coordinate end = new Coordinate(1, 1);

            // then
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> depthFirstSearchSolver.solve(
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
            DepthFirstSearchSolver depthFirstSearchSolver = new DepthFirstSearchSolver();
            Maze maze = new Maze(3, 3);
            Coordinate start = new Coordinate(0, 0);
            Coordinate end = null;

            // then
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> depthFirstSearchSolver.solve(
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

    @Nested
    @DisplayName("Поиск пути рекурсивным поиском в глубину.")
    class DFSRecSolver {
        @Test
        @DisplayName("Поиск пути.")
        void dfsRecSolve() {
            // given
            DepthFirstSearchRecSolver depthFirstSearchRecSolver = new DepthFirstSearchRecSolver();
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
            List<Coordinate> path = depthFirstSearchRecSolver.solve(
                maze,
                new Coordinate(0, 0),
                new Coordinate(2, 2)
            );

            // then
            assertThat(path).isNotNull().containsExactly(
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
        void dfsSolveRecNullMaze() {
            // given
            DepthFirstSearchRecSolver depthFirstSearchRecSolver = new DepthFirstSearchRecSolver();
            Maze maze = null;
            Coordinate start = new Coordinate(0, 0);
            Coordinate end = new Coordinate(1, 1);

            // then
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> depthFirstSearchRecSolver.solve(
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
        void dfsSolveRecNullStartCoord() {
            // given
            DepthFirstSearchRecSolver depthFirstSearchRecSolver = new DepthFirstSearchRecSolver();
            Maze maze = new Maze(3, 3);
            Coordinate start = null;
            Coordinate end = new Coordinate(1, 1);

            // then
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> depthFirstSearchRecSolver.solve(
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
        void dfsSolveRecNullEndCoord() {
            // given
            DepthFirstSearchRecSolver depthFirstSearchRecSolver = new DepthFirstSearchRecSolver();
            Maze maze = new Maze(3, 3);
            Coordinate start = new Coordinate(0, 0);
            Coordinate end = null;

            // then
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> depthFirstSearchRecSolver.solve(
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
}
