package edu.project2;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import edu.project2.render.SimpleRenderer;
import edu.project2.solvers.DepthFirstSearchSolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Отрисовка лабиринта.")
public class RenderersTest {
    @Test
    @DisplayName("Отрисовка лабиринта.")
    void simpleRendererMazeRender() {
        // given
        SimpleRenderer simpleRenderer = new SimpleRenderer();
        Maze maze = new Maze(5, 10);
        maze.deleteWall(1 ,2);
        maze.deleteWall(2, 1);
        maze.deleteWall(2, 3);
        maze.deleteWall(1, 6);
        maze.deleteWall(9, 6);
        maze.deleteWall(2, 18);

        // when
        String render = simpleRenderer.render(maze);

        // then
        assertThat(render).isNotNull().isEqualTo("""
            ╭───┬───┬─┬─┬─┬─┬─┬─╮
            │   │   │ │ │ │ │ ╵ │
            │ ╷ ├─┬─┼─┼─┼─┼─┼╴ ╶┤
            │ │ │ │ │ │ │ │ │ ╷ │
            ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
            │ │ │ │ │ │ │ │ │ │ │
            ├─┼─┼─┼─┼─┼─┼─┼─┼─┼─┤
            │ │ │ │ │ │ │ │ │ │ │
            ├─┼─┼─┴─┼─┼─┼─┼─┼─┼─┤
            │ │ │   │ │ │ │ │ │ │
            ╰─┴─┴───┴─┴─┴─┴─┴─┴─╯
            """);
    }

    @Test
    @DisplayName("Отрисовка лабиринта. Невозможное расположение стен.")
    void simpleRendererIncorrectWallType() {
        // given
        SimpleRenderer simpleRenderer = new SimpleRenderer();
        Maze maze = new Maze(5, 5);
        maze.deleteWall(1 ,2);
        maze.deleteWall(2, 1);
        maze.deleteWall(2, 3);
        maze.deleteWall(3, 2);


        // when
        Exception exception = assertThrows(
            RuntimeException.class,
            () -> simpleRenderer.render(maze)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo("Не может быть стены без \"соседей\"!");
    }

    @Test
    @DisplayName("Отрисовка лабиринта и пути в нем.")
    void simpleRendererPathRender() {
        // given
        SimpleRenderer simpleRenderer = new SimpleRenderer();
        Maze maze = new Maze(2, 2);
        maze.deleteWall(1 ,2);
        maze.deleteWall(2, 1);
        maze.deleteWall(2, 3);
        DepthFirstSearchSolver depthFirstSearchSolver = new DepthFirstSearchSolver();
        List<Coordinate> path = depthFirstSearchSolver.solve(
            maze,
            new Coordinate(0, 0),
            new Coordinate(1, 0)
        );

        // when
        String render = simpleRenderer.render(maze, path);

        // then
        assertThat(render).isNotNull().isEqualTo("""
            ╭───╮
            │\033[034m•\033[0m  │
            │\033[034m•\033[0m╷ │
            │\033[034m•\033[0m│ │
            ╰─┴─╯
            """);
    }

    @Test
    @DisplayName("Отрисовка лабиринта и пути в нем. Null-лабиринт.")
    void simpleRendererNullMaze() {
        // given
        SimpleRenderer simpleRenderer = new SimpleRenderer();
        Maze maze = null;
        List<Coordinate> path = List.of();

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> simpleRenderer.render(maze, path)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Лабиринт не может быть null!");
    }

    @Test
    @DisplayName("Отрисовка лабиринта и пути в нем. Null-путь.")
    void simpleRendererNullPath() {
        // given
        SimpleRenderer simpleRenderer = new SimpleRenderer();
        Maze maze = new Maze(5, 10);
        List<Coordinate> path = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> simpleRenderer.render(maze, path)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Путь не может быть null!");
    }
}
