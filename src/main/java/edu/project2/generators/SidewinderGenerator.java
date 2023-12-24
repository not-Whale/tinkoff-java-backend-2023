package edu.project2.generators;

import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.Random;

public class SidewinderGenerator implements Generator {
    private final Long seed;

    public SidewinderGenerator() {
        this.seed = null;
    }

    public SidewinderGenerator(long seed) {
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
            ArrayList<Integer> subRow = new ArrayList<>();
            for (int j = maze.getWidth() - 2; j > 0; j -= 2) {
                subRow.add(j);
                if ((random.nextBoolean() || j == 1) && i != 1) {
                    int index = subRow.get(random.nextInt(0, subRow.size()));
                    maze.deleteWall(i - 1, index);
                    subRow.clear();
                } else if (j != 1) {
                    maze.deleteWall(i, j - 1);
                }
            }
        }

        return maze;
    }
}
