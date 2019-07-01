package maze.command;

import maze.App;
import maze.graph.Node;

import java.util.*;

public class FindEscape extends Command {

    public FindEscape(int orderNumber, boolean showWhenNoMaze, boolean continueAfter, String name, App app) {
        super(orderNumber, showWhenNoMaze, continueAfter, name, app);
    }

    @Override
    public void action() {
        final int[][] maze = app.getMaze();
        Node start = app.getStart();
        Node end = app.getEnd();

        Deque<Node> front = new LinkedList<>();
        int[][] visited = new int[maze.length][maze[0].length];// array to achieve constant time
        front.addLast(start);
        int waveCounter = 0;
        Node waveDelimiter = start;
        visited[start.getRow()][start.getColumn()]=1;
        boolean endReached = false;
        while (front.size()>0){
            final Node node = front.pollFirst();
            endReached = addCurrentSiblings(maze, end, front, visited, node);
            if (node.equals(waveDelimiter)){
                waveCounter++;
                waveDelimiter = front.peekLast();
            }
            if (endReached){
                break;
            }
        }
        while (end!=null) {
            maze[end.getRow()][end.getColumn()]=2;
            end = end.getPrev();
        }
        System.out.println(waveCounter);
        app.display(maze);
    }

    private boolean addCurrentSiblings(int[][] maze, Node end, Deque<Node> front, int[][] visited, Node node) {
        for (Node candidate : node.calculateSiblings(maze)) {
            if (candidate.equals(end)){
                end.setPrev(node);
                return true;
            }
            if (visited[candidate.getRow()][candidate.getColumn()]!=1){
                candidate.setPrev(node);
                front.addLast(candidate);
                visited[candidate.getRow()][candidate.getColumn()]=1;
            }
        }
        return false;
    }
}
