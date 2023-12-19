package edu.project2.generators;

import edu.project2.generators.BinaryTreeGenerator;
import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import edu.project2.render.SimpleRenderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project2.maze.Cell.Type.PASSAGE;
import static edu.project2.maze.Cell.Type.WALL;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Генерация лабиринта.")
public class BinaryTreeGeneratorTest {
    @Test
    @DisplayName("Генерация с помощью построения бинарного дерева.")
    void binaryTreeGeneratorTest() {
        // given
        BinaryTreeGenerator generator = new BinaryTreeGenerator(1);

        // when
        Maze maze = generator.generate(3, 5);

        SimpleRenderer renderer = new SimpleRenderer();

        // then
        assertThat(maze.getWidth()).isEqualTo(11);
        assertThat(maze.getHeight()).isEqualTo(7);
        assertThat(maze.getGrid()).isEqualTo(new Cell[][] {{
            new Cell(0, 0, WALL),
            new Cell(0, 1, WALL),
            new Cell(0, 2, WALL),
            new Cell(0, 3, WALL),
            new Cell(0, 4, WALL),
            new Cell(0, 5, WALL),
            new Cell(0, 6, WALL),
            new Cell(0, 7, WALL),
            new Cell(0, 8, WALL),
            new Cell(0, 9, WALL),
            new Cell(0, 10, WALL)
        }, {
            new Cell(1, 0, WALL),
            new Cell(1, 1, PASSAGE),
            new Cell(1, 2, PASSAGE),
            new Cell(1, 3, PASSAGE),
            new Cell(1, 4, PASSAGE),
            new Cell(1, 5, PASSAGE),
            new Cell(1, 6, PASSAGE),
            new Cell(1, 7, PASSAGE),
            new Cell(1, 8, PASSAGE),
            new Cell(1, 9, PASSAGE),
            new Cell(1, 10, WALL)
        }, {
            new Cell(2, 0, WALL),
            new Cell(2, 1, PASSAGE),
            new Cell(2, 2, WALL),
            new Cell(2, 3, WALL),
            new Cell(2, 4, WALL),
            new Cell(2, 5, PASSAGE),
            new Cell(2, 6, WALL),
            new Cell(2, 7, PASSAGE),
            new Cell(2, 8, WALL),
            new Cell(2, 9, PASSAGE),
            new Cell(2, 10, WALL)
        }, {
            new Cell(3, 0, WALL),
            new Cell(3, 1, PASSAGE),
            new Cell(3, 2, PASSAGE),
            new Cell(3, 3, PASSAGE),
            new Cell(3, 4, WALL),
            new Cell(3, 5, PASSAGE),
            new Cell(3, 6, WALL),
            new Cell(3, 7, PASSAGE),
            new Cell(3, 8, WALL),
            new Cell(3, 9, PASSAGE),
            new Cell(3, 10, WALL)
        }, {
            new Cell(4, 0, WALL),
            new Cell(4, 1, PASSAGE),
            new Cell(4, 2, WALL),
            new Cell(4, 3, WALL),
            new Cell(4, 4, WALL),
            new Cell(4, 5, PASSAGE),
            new Cell(4, 6, WALL),
            new Cell(4, 7, WALL),
            new Cell(4, 8, WALL),
            new Cell(4, 9, PASSAGE),
            new Cell(4, 10, WALL)
        }, {
            new Cell(5, 0, WALL),
            new Cell(5, 1, PASSAGE),
            new Cell(5, 2, PASSAGE),
            new Cell(5, 3, PASSAGE),
            new Cell(5, 4, WALL),
            new Cell(5, 5, PASSAGE),
            new Cell(5, 6, PASSAGE),
            new Cell(5, 7, PASSAGE),
            new Cell(5, 8, WALL),
            new Cell(5, 9, PASSAGE),
            new Cell(5, 10, WALL)
        }, {
            new Cell(6, 0, WALL),
            new Cell(6, 1, WALL),
            new Cell(6, 2, WALL),
            new Cell(6, 3, WALL),
            new Cell(6, 4, WALL),
            new Cell(6, 5, WALL),
            new Cell(6, 6, WALL),
            new Cell(6, 7, WALL),
            new Cell(6, 8, WALL),
            new Cell(6, 9, WALL),
            new Cell(6, 10, WALL)
        }});
    }
}
