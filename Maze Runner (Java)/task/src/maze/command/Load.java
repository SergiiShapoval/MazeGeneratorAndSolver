package maze.command;

import maze.App;
import maze.graph.MazeWrapper;

import java.io.*;

public class Load extends FileCommand {


    public Load(int orderNumber, boolean showWhenNoMaze, boolean continueAfter, String name, App app) {
        super(orderNumber, showWhenNoMaze, continueAfter, name, app);
    }

    @Override
    public void action() {
        super.action();
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileName))) {
            final MazeWrapper o = (MazeWrapper) stream.readObject();
            app.setMazeWrapper(o);
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s does not exist\n", fileName);
        } catch (ClassNotFoundException | StreamCorruptedException e) {
            System.out.println("Cannot load the maze. It has an invalid format");
        } catch (IOException e) {
            System.out.println("Error during read file");
        }
    }
}
