package IO;

import logic.*;
import processing.core.PApplet;

import java.util.Timer;
import java.util.TimerTask;

public class Processing extends PApplet {
    private static enum modes {ENTRYGAME, GAME, GAMEOVER, MAINMENU}
    private static modes mode;

    Snake.directions heading;
    Grid grid;
    Snake snake;


    public static void main(String[] args) {
        PApplet.main("IO.Processing");
    }

    @Override
    public void settings() {
        size(840, 800);
    }

    @Override
    public void setup() {
        mode = modes.ENTRYGAME;
    }

    @Override
    public void draw() {
        background(0);

        switch (mode) {
            case ENTRYGAME:
                grid = new Grid(20, 20);
                snake = new Snake(grid);

                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        snake.move(heading, grid);
                        heading = null;
                        grid.updateGrid(snake);
                    }
                };
                timer.schedule(timerTask, 100, 100);

                grid.updateGrid(snake);
                grid.placeApple();

                noStroke();

                mode = modes.GAME;
                break;
            case GAME:
                textSize(30);
                fill(255);
                text("apples eaten: " + snake.getApplesEaten(), 20, 25);

                for (int i = 0; grid.getHeight() > i; i++) {
                    for (int j = 0; grid.getWidth() > j; j++) {
                        switch (grid.grid[j][i].getContent()) {
                            case HEAD:
                                fill(38, 168, 5);
                                square(j * 40, i * 40 + 40, 40);
                                break;
                            case BODY:
                                fill(19, 82, 3);
                                square(j * 40, i * 40 + 40, 40);
                                break;
                            case APPLE:
                                fill(255, 0, 0);
                                square(j * 40, i * 40 + 40, 40);
                        }
                    }
                }
                break;
            case GAMEOVER:
                textSize(100);
                fill(255,0,0);
                rectMode(RADIUS);
                text("GAME OVER",420,400);
                rectMode(CORNER);
                break;
            case MAINMENU:
        }
    }

    @Override
    public void keyPressed() {
        if (key == 'w') heading = Snake.directions.UP;
        if (key == 'a') heading = Snake.directions.LEFT;
        if (key == 's') heading = Snake.directions.DOWN;
        if (key == 'd') heading = Snake.directions.RIGHT;
    }


    public static void gameOver() {
        //System.exit(0);
        mode = modes.GAMEOVER;
    }
}