package logic;

import IO.ProcessingOut;

import java.util.ArrayList;

public class Snake {
    public static int headPosX;
    public static int headPosY;

    public static ArrayList<Integer> bodyArrayX = new ArrayList<>();
    public static ArrayList<Integer> bodyArrayY = new ArrayList<>();

    public enum directions {UP, DOWN, LEFT, RIGHT}


    private directions facing;

    public Snake() {
        facing = directions.UP;

        headPosX = Grid.getWidth() / 2;
        headPosY = Grid.getHeight() / 2;

        for (int i = 1; i < 4; i++) {
            bodyArrayX.add(headPosX);
            bodyArrayY.add(headPosY + i);
        }
    }

    public void move(directions heading) {
        if (heading == null) heading = facing;
        else facing = heading;

        int headNewX;
        int headNewY;

        switch (heading) {
            case UP:
                if (headPosY - 1 < 0) ProcessingOut.tempGameOver();
                headNewX = headPosX;
                headNewY = headPosY - 1;
                break;
            case DOWN:
                if (headPosY + 1 >= Grid.getHeight()) ProcessingOut.tempGameOver();
                headNewX = headPosX;
                headNewY = headPosY + 1;
                break;
            case LEFT:
                if (headPosX - 1 < 0) ProcessingOut.tempGameOver();
                headNewX = headPosX - 1;
                headNewY = headPosY;
                break;
            case RIGHT:
                if (headPosX + 1 >= Grid.getWidth()) ProcessingOut.tempGameOver();
                headNewX = headPosX + 1;
                headNewY = headPosY;
                break;
            default:
                // this is only so the compiler doesn't complain
                // if this case actually happens SOMEONE or SOMETHING severely fucked up
                headNewX = 0;
                headNewY = 0;
        }

        switch (Grid.grid[headNewX][headNewY].getContent()) {
            case BODY:
                ProcessingOut.tempGameOver();
                break;
            case EMPTY:
                for (int i = bodyArrayX.size() - 1; i > 0; i--) {
                    System.out.println(i);
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

                Grid.placeApple();
                break;
            default:
                System.out.println("how did we get here?");
        }
        System.out.println(bodyArrayX.toString());
        System.out.println(bodyArrayY.toString());
    }
}