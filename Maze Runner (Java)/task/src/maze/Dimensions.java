package maze;

import java.util.Scanner;

public class Dimensions {
    private Integer height;
    private Integer width;


    public Dimensions(Integer height, Integer width) {
        this.height = height;
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public static Dimensions readDimensions(Scanner scanner) {
        Integer height = null;
        Integer width = null;
        for (; ; ) {
            System.out.println("Please, enter the size of a maze");
            String line = scanner.nextLine();
            String[] dimensions = line.split(" ");
            if (dimensions.length > 3) {
                System.out.println("wrong input: should be one or two dimensions");
                continue;
            }
            try {
                height = Integer.valueOf(dimensions[0]);
                if (dimensions.length == 1) {
                    width = height;
                    break;
                }
                width = Integer.valueOf(dimensions[1]);
                if (width<3||height<3){
                    System.out.println("wrong input: number should be greater or equal to 3");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("wrong input: should be a number");
            }
        }
        return new Dimensions(height, width);
    }
}
