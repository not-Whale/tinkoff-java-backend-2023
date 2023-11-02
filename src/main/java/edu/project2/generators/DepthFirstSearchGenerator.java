package edu.project2.generators;

import edu.project2.maze.Cell;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class DepthFirstSearchGenerator implements Generator {
    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        Random random = new Random();
        Stack<Cell> stack = new Stack<>();
        ArrayList<Cell> remained = new ArrayList<>(List.of(maze.getPassages()));
        Collections.shuffle(remained);

        stack.push(remained.getFirst());
        remained.removeFirst();
        while (!remained.isEmpty()) {
            Cell current = stack.peek();
            Cell[] nearby = getNearbyMoves(maze, current, remained);
            while (nearby.length == 0) {
                current = stack.pop();
                nearby = getNearbyMoves(maze, current, remained);
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

    private Cell[] getNearbyMoves(Maze maze, Cell cell, ArrayList<Cell> remained) {
        ArrayList<Cell> nearbyCells = new ArrayList<>();

        if (cell.row() > 1) {
            Cell bottomCell = maze.getCell(cell.row() - 2, cell.col());
            if (remained.contains(bottomCell)) {
                nearbyCells.add(bottomCell);
            }
        }

        if (cell.row() < maze.getHeight() - 2) {
            Cell topCell = maze.getCell(cell.row() + 2, cell.col());
            if (remained.contains(topCell)) {
                nearbyCells.add(topCell);
            }
        }

        if (cell.col() > 1) {
            Cell rightCell = maze.getCell(cell.row(), cell.col() - 2);
            if (remained.contains(rightCell)) {
                nearbyCells.add(rightCell);
            }
        }

        if (cell.col() < maze.getWidth() - 2) {
            Cell leftCell = maze.getCell(cell.row(), cell.col() + 2);
            if (remained.contains(leftCell)) {
                nearbyCells.add(leftCell);
            }
        }

        return nearbyCells.toArray(new Cell[] {});
    }
}
