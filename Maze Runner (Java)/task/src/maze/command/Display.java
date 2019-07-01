package maze.command;

import maze.App;

public class Display extends Command {

    public Display(int orderNumber, boolean showWhenNoMaze, boolean continueAfter, String name, App app) {
        super(orderNumber, showWhenNoMaze, continueAfter, name, app);
    }

    @Override
    public void action() {
        app.display(app.getMaze());
    }


}
