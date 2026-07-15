package logic;

import IO.Processing;

import java.util.ArrayList;

public class Snake {
    public int headPosX;
    public int headPosY;

    protected int score = 0;

    protected ArrayList<Integer> bodyArrayX = new ArrayList<>();
    protected ArrayList<Integer> bodyArrayY = new ArrayList<>();

    public enum directions {UP, DOWN, LEFT, RIGHT}
    protected directions facing;


    public Snake(Grid grid) {
        facing = directions.UP;

        headPosX = grid.getWidth() / 2;
        headPosY = grid.getHeight() / 2;

        for (int i = 1; i < 4; i++) {
            bodyArrayX.add(headPosX);
            bodyArrayY.add(headPosY + i);
        }
    }

    public int getScore() {
        return score;
    }

    public void move(directions heading, Grid grid) {
        if (heading == null) heading = facing;
        //else facing = heading;

        int headNewX;
        int headNewY;

        switch (heading) {
            case UP:
                if (headPosY - 1 < 0) Processing.gameOver();
                headNewX = headPosX;
                headNewY = headPosY - 1;
                break;
            case DOWN:
                if (headPosY + 1 >= grid.getHeight()) Processing.gameOver();
                headNewX = headPosX;
                headNewY = headPosY + 1;
                break;
            case LEFT:
                if (headPosX - 1 < 0) Processing.gameOver();
                headNewX = headPosX - 1;
                headNewY = headPosY;
                break;
            case RIGHT:
                if (headPosX + 1 >= grid.getWidth()) Processing.gameOver();
                headNewX = headPosX + 1;
                headNewY = headPosY;
                break;
            default:
                // this is only so the compiler doesn't complain
                // if this case actually happens SOMEONE or SOMETHING severely fucked up
                headNewX = 0;
                headNewY = 0;
        }

        if (headNewX == bodyArrayX.getFirst() && headNewY == bodyArrayY.getFirst()) {
            // just doing this whole thing a second time is a very stupid solution to
            // being able to reverse into yourself and die, but I'm too tired to come up
            // with a more sophisticated solution
            switch (facing) {
                case UP:
                    if (headPosY - 1 < 0) Processing.gameOver();
                    headNewX = headPosX;
                    headNewY = headPosY - 1;
                    break;
                case DOWN:
                    if (headPosY + 1 >= grid.getHeight()) Processing.gameOver();
                    headNewX = headPosX;
                    headNewY = headPosY + 1;
                    break;
                case LEFT:
                    if (headPosX - 1 < 0) Processing.gameOver();
                    headNewX = headPosX - 1;
                    headNewY = headPosY;
                    break;
                case RIGHT:
                    if (headPosX + 1 >= grid.getWidth()) Processing.gameOver();
                    headNewX = headPosX + 1;
                    headNewY = headPosY;
                    break;
                default:
                    // this is only so the compiler doesn't complain
                    // if this case actually happens SOMEONE or SOMETHING severely fucked up
                    headNewX = 0;
                    headNewY = 0;
            }
        }else facing = heading;

        switch (grid.grid[headNewX][headNewY].getContent()) {
            case BODY:
                if (!(headNewX == bodyArrayX.getLast() && headNewY == bodyArrayY.getLast())) {
                    Processing.gameOver();
                    break;
                }
            case EMPTY:
                for (int i = bodyArrayX.size() - 1; i > 0; i--) {
                    bodyArrayX.set(i, bodyArrayX.get(i - 1));
                    bodyArrayY.set(i, bodyArrayY.get(i - 1));
                }
                bodyArrayX.set(0, headPosX);
                bodyArrayY.set(0, headPosY);

                headPosX = headNewX;
                headPosY = headNewY;
                break;
            case APPLE:
                bodyArrayX.add(bodyArrayX.getLast());
                bodyArrayY.add(bodyArrayY.getLast());

                for (int i = bodyArrayX.size() - 2; i > 0; i--) {
                    bodyArrayX.set(i, bodyArrayX.get(i - 1));
                    bodyArrayY.set(i, bodyArrayY.get(i - 1));
                }
                bodyArrayX.set(0, headPosX);
                bodyArrayY.set(0, headPosY);

                headPosX = headNewX;
                headPosY = headNewY;

                grid.updateGrid(this);

                score += 50;
                grid.placeApple();
                break;
            default:
                System.out.println("how did we get here?");
        }
    }
}