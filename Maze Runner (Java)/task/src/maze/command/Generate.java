package maze.command;

import maze.App;
import maze.Dimensions;
import maze.graph.MazeWrapper;
import maze.graph.Node;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Generate extends Command {

    public Generate(int orderNumber, boolean showWhenNoMaze, boolean continueAfter, String name, App app) {
        super(orderNumber, showWhenNoMaze, continueAfter, name, app);
    }

    @Override
    public void action() {
        Dimensions dimension = Dimensions.readDimensions(app.getScanner());
        int[][] maze = new int[dimension.getHeight()][dimension.getWidth()];
        Random random = new Random();
        app.fillMaze(maze);
        final Deque<Node> startAndEnd = generatePathByPrim(random, maze);
        app.setMazeWrapper(new MazeWrapper(maze, startAndEnd.getFirst(), startAndEnd.getLast()));
        app.display(maze);
    }

    public static Deque<Node> generatePathByRandomMoves(Random random, int[][] maze) {
        final int min = Math.min(maze.length, maze[0].length); // choose min to avoid out of bound
        Node current = defineStartNode(min, random);
        final LinkedList<Node> startAndEnd = new LinkedList<>();
        startAndEnd.addFirst(current);
        maze[current.getRow()][current.getColumn()] = 0; // mark visited on board and keep visited on it, to minimise space
        int nodeCounter = 1;
        do {
            Node next = chooseNextNode(nodeCounter == 1, current, random);
            while (maze[next.getRow()][next.getColumn()] == 0) {
                next = chooseNextNode(nodeCounter == 1, current, random);
            }
            nodeCounter++;
            current = next;
            maze[current.getRow()][current.getColumn()] = 0;
        } while (isAlreadyOnTheEdge(maze, current));
        startAndEnd.addLast(current);
        return startAndEnd;
    }

    private static boolean isAlreadyOnTheEdge(int[][] maze, Node current) {
        return current.getColumn() != 0 && current.getRow() != 0 && current.getRow() != maze.length - 1 && current.getColumn() != maze[0].length - 1;
    }

    private static Node chooseNextNode(boolean isOnlyOneNode, Node current, Random random) {
        if (isOnlyOneNode) {
            if (current.getColumn() == 0) {
                return new Node(current.getRow(), 1);
            }
            return new Node(1, current.getColumn());
        }
        switch (random.nextInt(11)) {
            case 0:
            case 1:
            case 2:
                return right(current);//increased right chances
            case 4:
            case 5:
            case 6:
                return down(current); //increased down chances
            case 9: // up
                return new Node(current.getRow() - 1, current.getColumn());
            case 10: // left
                return new Node(current.getRow(), current.getColumn() - 1);
            default:
                return current;
        }
    }

    @NotNull
    private static Node down(Node current) {
        return new Node(current.getRow() + 1, current.getColumn());
    }

    @NotNull
    private static Node right(Node current) {
        return new Node(current.getRow(), current.getColumn() + 1);
    }

    public Deque<Node> generatePathByPrim(Random random, int[][] maze) {
        Deque<Node> result = new LinkedList<>();

        final int maxRow = maze.length;
        final int maxColumn = maze[0].length;
        int[][] weightedMaze = new int[maxRow][maxColumn];
        fillByRandom(random, weightedMaze);
        final int min = Math.min(maxRow, maxColumn); // choose min to avoid out of bound

        Node current = defineStartNode(min, random);
        Queue<Node> queue = new PriorityQueue<>();
        queue.add(current);
        result.addFirst(current);
        int qty = 0;
        while (queue.size() > 0 && qty < maxColumn * maxRow / 2) {
            current = queue.poll();
            final List<Node> siblings = current.calculateWeightedSiblings(weightedMaze);
            if (alreadyAddedSiblings(maze, siblings) <= 1 && (!onMinEdges(current) || qty == 0)) {
                maze[current.getRow()][current.getColumn()] = 0;
                queue.addAll(siblings);
                qty++;
                if (onMaxEdges(maxRow, maxColumn, current)) {
                    break;
                }
            }
        }
        result.addLast(current);
        return result;
    }

    private int alreadyAddedSiblings(int[][] maze, List<Node> siblings) {
        int alreadyAddedSiblingsQty = 0;
        for (Node adjacent : siblings) {
            if (maze[adjacent.getRow()][adjacent.getColumn()] == 0) {
                alreadyAddedSiblingsQty++;
            }
        }
        return alreadyAddedSiblingsQty;
    }

    private boolean onMinEdges(Node current) {
        return current.getColumn() == 0 || current.getRow() == 0;
    }

    private boolean onMaxEdges(int maxRow, int maxColumn, Node current) {
        return current.getColumn() == maxColumn - 1 || current.getRow() == maxRow - 1;
    }

    private static void fillByRandom(Random random, int[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = random.nextInt(maze.length * maze[i].length);
            }
        }
    }

    @NotNull
    private static Node defineStartNode(int min, Random random) {
        int randomStart = random.nextInt(min - 2) + 1;
        if (random.nextBoolean()) {
            return new Node(0, randomStart);
        } else {
            return new Node(randomStart, 0);
        }
    }

}
