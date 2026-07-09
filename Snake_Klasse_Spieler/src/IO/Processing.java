package IO;

import logic.*;
import processing.core.PApplet;

import java.util.Timer;
import java.util.TimerTask;

public class Processing extends PApplet {
    enum modes {ENTRYGAME, GAME, ENTRYGAMEOVER, GAMEOVER, MAINMENU}
    static modes mode;

    Snake.directions heading;
    Grid grid;
    Snake snake;

    Timer timer;
    TimerTask timerTask;

    Button buttonPlayAgain;


    public static void main(String[] args) {
        PApplet.main("IO.Processing");
    }

    @Override
    public void settings() {
        size(800, 840);
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
                initializeButtons();
                grid = new Grid(20, 20);
                snake = new Snake(grid);

                timer = new Timer();
                timerTask = new TimerTask() {
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
                grid.updateGrid(snake);

                noStroke();

                mode = modes.GAME;
                break;
            case GAME:
                textSize(30);
                fill(255);
                textAlign(LEFT, BASELINE);
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
            case ENTRYGAMEOVER:
                timer.cancel();
                mode = modes.GAMEOVER;
                break;
            case GAMEOVER:
                textSize(100);
                fill(255,0,0);
                textAlign(CENTER, CENTER);
                text("GAME OVER",420,400);
                buttonPlayAgain.execute(this);
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
        mode = modes.ENTRYGAMEOVER;
    }

    private void initializeButtons() {
                buttonPlayAgain = new Button(350, 575, 100, 50, "Play Again"){
                    @Override
                    void executeFunction() {Processing.mode = modes.ENTRYGAME;}
                };
    }
}
