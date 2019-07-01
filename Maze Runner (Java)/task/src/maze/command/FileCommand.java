package maze.command;

import maze.App;
import maze.graph.Node;

import java.io.Serializable;
import java.util.Set;

public class FileCommand extends Command {

    protected String fileName;

    public FileCommand(int orderNumber, boolean showWhenNoMaze, boolean continueAfter, String name, App app) {
        super(orderNumber, showWhenNoMaze, continueAfter, name, app);
    }

    @Override
    public void action() {
        System.out.println("Please, enter a filename to " + name);
        fileName = app.getScanner().nextLine();
    }
}