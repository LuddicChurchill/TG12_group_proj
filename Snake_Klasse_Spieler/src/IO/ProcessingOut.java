package IO;

import logic.*;
import processing.core.PApplet;

import java.util.Timer;
import java.util.TimerTask;

public class ProcessingOut extends PApplet {
    Snake.directions heading;
    Snake snake = new Snake();

    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run(){
            snake.move(heading);
            heading = null;
            Grid.updateGrid();
            tempOut();

        }
    };

    public static void main(String[] args) {
        PApplet.main("IO.ProcessingOut");
    }

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {
        Grid.initGrid();
        timer.schedule(timerTask, 1000, 1000);

        Snake snake = new Snake();
        Grid.placeApple();
        tempOut();
    }

    @Override
    public void draw() {
        background(0);
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

    public static void tempOut() {
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
}