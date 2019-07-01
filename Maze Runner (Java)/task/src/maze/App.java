package maze;

import maze.command.*;
import maze.graph.MazeWrapper;
import maze.graph.Node;

import java.util.*;

public class App {
    private MazeWrapper mazeWrapper = new MazeWrapper();
    private final Scanner scanner;
    private final List<Command> commands;

    public App(Scanner scanner) {
        this.scanner = scanner;
        commands = generateCommands();
    }

    private List<Command> generateCommands() {
        List<Command> res = new ArrayList<>();
        res.add(new Generate(1,true,true,"Generate a new maze",this));
        res.add(new Load(2,true,true,"Load a maze",this));
        res.add(new Save(3,false,true,"Save the maze",this));
        res.add(new Display(4, false, true, "Display the maze", this));
        res.add(new FindEscape(5,false,true,"Find the escape",this));
        res.add(new Exit(0,true,false,"Exit",this));
        return res;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public MazeWrapper getMazeWrapper() {
        return mazeWrapper;
    }

    public void setMazeWrapper(MazeWrapper mazeWrapper) {
        this.mazeWrapper = mazeWrapper;
    }

    public int[][] getMaze() {
        return mazeWrapper.getMaze();
    }

    public Node getStart() {
        return mazeWrapper.getStart();
    }

    public Node getEnd() {
        return mazeWrapper.getEnd();
    }

    public List<Command> availableCommands() {
        List<Command> res = new ArrayList<>();
        for (Command command : commands) {
            if (getMaze()!=null) {
                res.add(command);
                continue;
            }
            if (command.isShowWhenNoMaze()){
                res.add(command);
            }
        }
        return res;
    }

    public void printMenu(List<Command> commands) {
        System.out.println("=== Menu ===");
        for (Command command : commands) {
            System.out.println(command);
        }
    }

    public void fillMaze(int[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            Arrays.fill(maze[i], 1);
        }
    }

    public void display(int[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                String toPrint;
                if (maze[i][j] == 0) {
                    toPrint = "  ";
                } else if (maze[i][j] == 1){
                    toPrint = "██";
                } else {
                    toPrint = "//";
                }
                System.out.print(toPrint);
            }
            System.out.println();
        }
    }
}
