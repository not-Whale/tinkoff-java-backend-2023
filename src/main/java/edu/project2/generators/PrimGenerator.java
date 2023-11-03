package edu.project2.generators;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import edu.project2.utils.MazeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimGenerator implements Generator {
    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        Random random = new Random();
        ArrayList<Cell> remained = new ArrayList<>(List.of(maze.getPassages()));

        ArrayList<Cell> visited = new ArrayList<>(){{ add(remained.removeFirst()); }};
        while (!remained.isEmpty()) {
            Cell currentCell = visited.get(random.nextInt(visited.size()));
            ArrayList<Cell> moves = new ArrayList<>(List.of(MazeUtils.getMovesWithWhitelist(
                maze,
                currentCell,
                2,
                remained))
            );
            if (!moves.isEmpty()) {
                Cell move = moves.get(random.nextInt(moves.size()));
                deleteWall(maze, currentCell, move);
                visited.add(move);
                remained.remove(move);
            }
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
