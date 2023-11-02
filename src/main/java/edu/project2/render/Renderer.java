package edu.project2.render;

import edu.project2.maze.Coordinate;
import edu.project2.maze.Maze;
import java.util.List;

public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
