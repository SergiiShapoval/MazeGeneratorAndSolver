package maze.command;

import maze.App;

public class Exit extends Command {


    public Exit(int orderNumber, boolean showWhenNoMaze, boolean continueAfter, String name, App app) {
        super(orderNumber, showWhenNoMaze, continueAfter, name, app);
    }

    @Override
    public void action() {
        System.out.println("Bye!");
    }
}
