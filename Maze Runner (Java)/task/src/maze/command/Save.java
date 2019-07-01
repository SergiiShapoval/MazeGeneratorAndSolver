package maze.command;

import maze.App;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Save extends FileCommand {

    public Save(int orderNumber, boolean showWhenNoMaze, boolean continueAfter, String name, App app) {
        super(orderNumber, showWhenNoMaze, continueAfter, name, app);
    }

    @Override
    public void action() {
        super.action();
        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName))){
            stream.writeObject(app.getMazeWrapper());
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s does not exist\n", fileName);
        } catch (IOException e) {
            System.out.println("Error during read file");
        }
    }
}
