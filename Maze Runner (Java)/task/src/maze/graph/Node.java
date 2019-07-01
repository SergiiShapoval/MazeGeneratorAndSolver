package maze.graph;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable, Comparable<Node> {

    private final int row;
    private final int column;
    private int weight;
    private Node prev;

    public Node(int row, int column) {
        this.row = row;
        this.column = column;
    }

    private Node(int row, int column, int weight) {
        this.row = row;
        this.column = column;
        this.weight = weight;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (row != node.row) return false;
        return column == node.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    /**
     * right (0,1)
     * down (1,0)
     * left (0,-1)
     * up (-1,0)
     */
    private int[] rowDelta = new int[]{0, 1, 0, -1};
    private int[] columnDelta = new int[]{1, 0, -1, 0};

    /**
     * as cell could have maximum 4 siblings it could be easily calculated
     *
     * @param maze without optimal path, only walls (1) and free cells (0)
     * @return list of siblings for current node
     */
    public List<Node> calculateSiblings(int[][] maze) {

        final ArrayList<Node> result = new ArrayList<>();
        for (int deltaI = 0; deltaI <rowDelta.length; deltaI++) {
            int row = this.row + rowDelta[deltaI];
            int column = this.column + columnDelta[deltaI];
            if (isValid(row, maze.length)
                    && isValid(column, maze[0].length)
                    && maze[row][column] == 0) {
                result.add(new Node(row, column));
            }
        }
        return result;
    }

    public List<Node> calculateWeightedSiblings(int[][] weightedMaze) {
        final ArrayList<Node> result = new ArrayList<>();
        for (int deltaI = 0; deltaI <rowDelta.length; deltaI++) {
            int row = this.row + rowDelta[deltaI];
            int column = this.column + columnDelta[deltaI];
            if (isValid(row, weightedMaze.length)
                    && isValid(column, weightedMaze[0].length)) {
                result.add(new Node(row, column, weightedMaze[row][column]));
            }
        }
        return result;
    }

    public static boolean isValid(int position, int upperLimit) {
        if (position < 0) {
            return false;
        }
        return position < upperLimit;
    }

    @Override
    public String toString() {
        return "Node{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public int compareTo(@NotNull Node that) {
        return this.weight-that.weight;
    }
}
