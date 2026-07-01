import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\u001B[36m"+"Hello world!"+"\u001B[36m");
    }

    private static void tempOut() {
        for (int i = 0; i < Grid.getHeight(); i++) {
            for (int j = 0; j < Grid.getWidth(); j++) {
                switch (Grid.grid[j][i].getContent()) {
                    case EMPTY:
                        System.out.println("_");
                        break;
                    case HEAD:
                        System.out.println("\u001B[36m"+"H"+"\u001B[0m");
                        break;
                    case BODY:
                        System.out.println("\u001B[32m"+"B"+"\u001B[0m");
                        break;
                    case APPLE:
                        System.out.println("\u001B[31m"+"A"+"\u001B[0m");
                }
            }
            System.out.println();
        }
    }

    public static String tempIn() {
        Scanner scan = new Scanner(System.in).useLocale(Locale.US);
        return scan.next();
    }

    public static void tempGameOver() throws Exception {
        throw new Exception("Game Over");
    }
}