package logic;

import IO.Processing;

public class CannibalSnake extends Snake {
    public CannibalSnake(Grid grid) {
        super(grid);
    }

    @Override
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

        if (headNewX == bodyArrayX.get(0) && headNewY == bodyArrayY.get(0)) {
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
                int indexToRemove = 0;
                for (int i = 0; i < bodyArrayX.size(); i++) {
                    if (bodyArrayX.get(i) == headNewX && bodyArrayY.get(i) == headNewY) {
                        indexToRemove = i;
                    }
                }

                for (int i = bodyArrayX.size() - 1; i >= indexToRemove + 1; i--) {
                    bodyArrayX.remove(i);
                    bodyArrayY.remove(i);
                }

                //break;
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
                bodyArrayX.add(bodyArrayX.get(bodyArrayX.size() - 1));
                bodyArrayY.add(bodyArrayY.get(bodyArrayY.size() - 1));

                for (int i = bodyArrayX.size() - 2; i > 0; i--) {
                    bodyArrayX.set(i, bodyArrayX.get(i - 1));
                    bodyArrayY.set(i, bodyArrayY.get(i - 1));
                }
                bodyArrayX.set(0, headPosX);
                bodyArrayY.set(0, headPosY);

                headPosX = headNewX;
                headPosY = headNewY;

                grid.updateGrid(this);

                applesEaten++;
                grid.placeApple();
                break;
            default:
                System.out.println("how did we get here?");
        }
    }
}