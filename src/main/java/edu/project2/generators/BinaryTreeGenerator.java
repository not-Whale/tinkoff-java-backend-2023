package edu.project2.generators;

import edu.project2.maze.Maze;
import java.util.Random;

public class BinaryTreeGenerator implements Generator {
    private final Long seed;

    public BinaryTreeGenerator() {
        this.seed = null;
    }

    public BinaryTreeGenerator(long seed) {
        this.seed = seed;
    }

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        Random random;
        if (seed == null) {
            random = new Random();
        } else {
            random = new Random(seed);
        }

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
