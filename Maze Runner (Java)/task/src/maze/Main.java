package maze;

import maze.command.Command;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {

        Scanner scanner = new Scanner(System.in);

        App app = new App(scanner);

        for (;;){
            List<Command> availableCommands = app.availableCommands();
            app.printMenu(availableCommands);

            int chosenCommand;
            try {
                chosenCommand = Integer.valueOf(scanner.nextLine());
                if (chosenCommand < 0 || chosenCommand >= availableCommands.size()){
                    System.out.println("Incorrect option. Please try again");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect option. Please try again");
                continue;
            }

            Collections.sort(availableCommands);

            Command command = availableCommands.get(chosenCommand);
            command.action();
            if (!command.isContinueAfter()){
                break;
            }
        }
    }
}
