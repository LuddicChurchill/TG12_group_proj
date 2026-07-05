import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Grid.initGrid();

        Snake snake = new Snake();
        Grid.placeApple();

        tempOut();


        while (true) {
        snake.move(tempIn());
        Grid.updateGrid();
        tempOut();
        }
    }

    private static void tempOut() {
        for (int i = 0; i < Grid.getHeight(); i++) {
                if(i>=10)System.out.print(i + ": ");
                else System.out.print(i + ":  ");
            for (int j = 0; j < Grid.getWidth(); j++) {
                switch (Grid.grid[j][i].getContent()) {
                    case EMPTY:
                        System.out.print("_ ");
                        break;
                    case HEAD:
                        System.out.print("\u001B[36m" + "H " + "\u001B[0m");
                        break;
                    case BODY:
                        System.out.print("\u001B[32m" + "B " + "\u001B[0m");
                        break;
                    case APPLE:
                        System.out.print("\u001B[31m" + "A " + "\u001B[0m");
                }
            }
            System.out.println();
        }
    }

    public static Snake.directions tempIn() {
        Scanner scan = new Scanner(System.in).useLocale(Locale.US);
        return switch (scan.next()) {
            case "up", "w" -> Snake.directions.UP;
            case "down", "s" -> Snake.directions.DOWN;
            case "left", "a" -> Snake.directions.LEFT;
            case "right", "d" -> Snake.directions.RIGHT;
            default -> null;
        };
    }

    public static void tempGameOver() throws Exception {
        throw new Exception("Game Over");
    }
}