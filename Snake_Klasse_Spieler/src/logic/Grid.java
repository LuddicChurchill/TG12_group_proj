package logic;

import java.util.Random;

public class Grid {
    static Random r = new Random();

    private final int width;
    private final int height;

    private int appleX;
    private int appleY;

    public Cell[][] grid;


    public Grid (int width, int height) {
        this.width = width;
        this.height = height;

        grid = new Cell[width][height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[j][i] = new Cell(Cell.states.EMPTY);
            }
        }

        placeApple();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void placeApple() {
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

    public void updateGrid(Snake snake) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[j][i].setContent(Cell.states.EMPTY);
            }
        }

        grid[appleX][appleY].setContent(Cell.states.APPLE);

        grid[snake.headPosX][snake.headPosY].setContent(Cell.states.HEAD);

        for (int i = 0; i < snake.bodyArrayX.size(); i++) {
            grid[snake.bodyArrayX.get(i)][snake.bodyArrayY.get(i)].setContent(Cell.states.BODY);
        }
    }
}