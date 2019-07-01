package maze.command;

import maze.App;
import org.jetbrains.annotations.NotNull;

public abstract class Command implements Comparable<Command>{

    protected final App app;
    private final int orderNumber;
    private final boolean showWhenNoMaze, continueAfter;
    protected final String name;

    public Command(int orderNumber, boolean showWhenNoMaze, boolean continueAfter, String name, App app) {
        this.orderNumber = orderNumber;
        this.showWhenNoMaze = showWhenNoMaze;
        this.continueAfter = continueAfter;
        this.name = name;
        this.app = app;
    }

    @Override
    public int compareTo(@NotNull Command that) {
        return this.orderNumber - that.orderNumber;
    }

    @Override
    public String toString() {
        return this.orderNumber + ". " + this.name;
    }

    public boolean isShowWhenNoMaze() {
        return showWhenNoMaze;
    }

    public boolean isContinueAfter() {
        return continueAfter;
    }

    public abstract void action();
}
