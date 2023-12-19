package edu.project2.generators;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import edu.project2.utils.MazeUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class DepthFirstSearchGenerator implements Generator {
    private final Long seed;

    public DepthFirstSearchGenerator() {
        this.seed = null;
    }

    public DepthFirstSearchGenerator(long seed) {
        this.seed = seed;
    }

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        Stack<Cell> stack = new Stack<>();
        ArrayList<Cell> remained = new ArrayList<>(List.of(maze.getPassages()));
        Collections.shuffle(remained);

        Random random;
        if (seed == null) {
           random = new Random();
        } else {
            random = new Random(seed);
        }

        stack.push(remained.getFirst());
        remained.removeFirst();
        while (!remained.isEmpty()) {
            Cell current = stack.peek();
            Cell[] nearby = MazeUtils.getMovesWithWhitelist(maze, current, 2, remained);
            while (nearby.length == 0) {
                current = stack.pop();
                nearby = MazeUtils.getMovesWithWhitelist(maze, current, 2, remained);
            }

            Cell move = nearby[random.nextInt(0, nearby.length)];
            deleteWall(maze, current, move);

            stack.push(move);
            remained.remove(move);
        }

        return maze;
    }

    private void deleteWall(Maze maze, Cell a, Cell b) {
        if (Math.abs(a.row() - b.row()) != 2 && Math.abs(a.col() - b.col()) != 2) {
            throw new IllegalArgumentException("Ячейки не являются соседними!");
        }

        int i = (a.row() + b.row()) / 2;
        int j = (a.col() + b.col()) / 2;
        maze.deleteWall(i, j);
    }
}
