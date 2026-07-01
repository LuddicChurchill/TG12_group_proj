import java.util.Random;

public class Grid {
    static Random r = new Random();

    private static int width = 20;
    private static int height = 20;

    private static int appleX;
    private static int appleY;

    public static Cell[][] grid = new Cell[width][height];

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void placeApple() {
        while (true) {
            int tempX = r.nextInt(width);
            int tempY = r.nextInt(height);

            if (grid[tempX][tempY].isEmpty()) {
                appleX = tempX;
                appleY = tempY;
                break;
            }
        }
    }

    public static void updateGrid() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[j][i].setContent(Cell.states.EMPTY);
            }
        }

        grid[appleX][appleY].setContent(Cell.states.APPLE);

        grid[Snake.headPosX][Snake.headPosY].setContent(Cell.states.HEAD);

        for(int i = 0; i < Snake.bodyArrayX.size(); i++) {
            grid[Snake.bodyArrayX.get(i)][Snake.bodyArrayY.get(i)].setContent(Cell.states.BODY);
        }
    }
}