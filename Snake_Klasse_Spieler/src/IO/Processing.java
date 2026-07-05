package IO;

import logic.*;
import processing.core.PApplet;

import java.util.Timer;
import java.util.TimerTask;

public class Processing extends PApplet {
    Snake.directions heading;
    Grid grid = new Grid(20, 20);
    Snake snake = new Snake(grid);


    public static void main(String[] args) {
        PApplet.main("IO.Processing");
    }

    @Override
    public void settings() {
        size(800, 840);
    }

    @Override
    public void setup() {
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

        grid.placeApple();

        noStroke();
        textSize(30);
    }

    @Override
    public void draw() {
        background(0);

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
    }

    @Override
    public void keyPressed() {
        if (key == 'w') heading = Snake.directions.UP;
        if (key == 'a') heading = Snake.directions.LEFT;
        if (key == 's') heading = Snake.directions.DOWN;
        if (key == 'd') heading = Snake.directions.RIGHT;
    }


    public static void tempGameOver() {
        System.exit(0);
    }
}