package edu.project2.solvers;

import edu.project2.maze.Cell;
import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearchRecSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        ArrayList<Cell> remained = new ArrayList<>(List.of(maze.getPassages()));
        Cell startCell = maze.getCell(start.row() * 2 + 1, start.col() * 2 + 1);
        Cell endCell = maze.getCell(end.row() * 2 + 1, end.col() * 2 + 1);

        DepthFirstSearchRec bfsRec = new DepthFirstSearchRec(maze, remained);
        return bfsRec.search(startCell, endCell);
    }

    private class DepthFirstSearchRec {
        private List<Cell> remained;

        private Maze maze;

        public DepthFirstSearchRec(Maze maze, List<Cell> remained) {
            this.maze = maze;
            this.remained = remained;
        }

        public List<Coordinate> search(Cell currentCell, Cell endCell) {
            if (currentCell.equals(endCell)) {
                return new ArrayList<>(){{ add(new Coordinate(endCell.row(), endCell.col())); }};
            }

            remained.remove(currentCell);
            Cell[] nearbyMoves = getNearbyMoves(currentCell);
            for (Cell move : nearbyMoves) {
                List<Coordinate> path = search(move, endCell);
                if (path != null) {
                    path.add(new Coordinate(currentCell.row(), currentCell.col()));
                    return path;
                }
            }

            return null;
        }

        private Cell[] getNearbyMoves(Cell cell) {
            ArrayList<Cell> nearbyCells = new ArrayList<>();

            if (cell.row() > 0) {
                Cell bottomCell = maze.getCell(cell.row() - 1, cell.col());
                if (remained.contains(bottomCell)) {
                    nearbyCells.add(bottomCell);
                }
            }

            if (cell.row() < maze.getHeight() - 1) {
                Cell topCell = maze.getCell(cell.row() + 1, cell.col());
                if (remained.contains(topCell)) {
                    nearbyCells.add(topCell);
                }
            }

            if (cell.col() > 0) {
                Cell rightCell = maze.getCell(cell.row(), cell.col() - 1);
                if (remained.contains(rightCell)) {
                    nearbyCells.add(rightCell);
                }
            }

            if (cell.col() < maze.getWidth() - 1) {
                Cell leftCell = maze.getCell(cell.row(), cell.col() + 1);
                if (remained.contains(leftCell)) {
                    nearbyCells.add(leftCell);
                }
            }

            return nearbyCells.toArray(new Cell[] {});
        }
    }
}
