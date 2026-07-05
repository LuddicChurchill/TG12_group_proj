package logic;

import java.util.Random;

public class Grid {
    static Random r = new Random();

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    private static int appleX;
    private static int appleY;

    public static Cell[][] grid = new Cell[WIDTH][HEIGHT];

    public static void initGrid() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[j][i] = new Cell(Cell.states.EMPTY);
            }
        }
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static void placeApple() {
        while (true) {
            int tempX = r.nextInt(WIDTH);
            int tempY = r.nextInt(HEIGHT);

            if (grid[tempX][tempY].isEmpty()) {
                appleX = tempX;
                appleY = tempY;
                updateGrid();
                break;
            }
        }
    }

    public static void updateGrid() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
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