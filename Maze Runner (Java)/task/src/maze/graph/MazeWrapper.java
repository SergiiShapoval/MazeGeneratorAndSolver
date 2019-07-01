package maze.graph;

import java.io.Serializable;
import java.util.*;

public class MazeWrapper implements Serializable {

    private int[][] maze;
    private Node start;
    private Node end;

    public MazeWrapper() {
    }

    public MazeWrapper(int[][] maze, Node start, Node end) {
        this.maze = maze;
        this.start = start;
        this.end = end;
    }

    public int[][] getMaze() {
        if (maze == null) {
            return null;
        }
        int[][] copy = new int[maze.length][];
        for (int i = 0; i < maze.length; i++) {
            copy[i] = Arrays.copyOf(maze[i], maze[i].length);
        }
        return copy;
    }

    public Node getStart() {
        return new Node(start.getRow(), start.getColumn());
    }

    public Node getEnd() {
        return new Node(end.getRow(), end.getColumn());
    }
}