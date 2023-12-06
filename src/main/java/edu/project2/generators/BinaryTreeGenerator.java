package edu.project2.generators;

import edu.project2.maze.Maze;
import java.util.Random;

public class BinaryTreeGenerator implements Generator {
    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        Random random = new Random();

        for (int i = 1; i < maze.getHeight() - 1; i += 2) {
            for (int j = 1; j < maze.getWidth() - 1; j += 2) {
                if ((random.nextBoolean() || j == 1) && i != 1) {
                    maze.deleteWall(i - 1, j);
                } else if (j != 1) {
                    maze.deleteWall(i, j - 1);
                }
            }
        }

        return maze;
    }
}
