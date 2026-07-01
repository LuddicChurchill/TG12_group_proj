import java.util.ArrayList;

public class Snake {
    public static int headPosX;
    public static int headPosY;

    public static ArrayList<Integer> bodyArrayX = new ArrayList<>();
    public static ArrayList<Integer> bodyArrayY = new ArrayList<>();

    private enum directions {UP, DOWN, LEFT, RIGHT}

    ;
    private directions facing;

    Snake() {
        facing = directions.UP;

        headPosX = Grid.getWidth() / 2;
        headPosY = Grid.getHeight() / 2;

        for (int i = 1; i < 4; i++) {
            bodyArrayX.add(headPosX);
            bodyArrayY.add(headPosY + i);
        }

        Grid.updateGrid();
    }

    public void move(directions heading) throws Exception {
        switch (heading) {
            case UP:
                if (headPosY-1 < 0) Main.tempGameOver();
                break;
            case DOWN:
                if (headPosY+1 >= Grid.getHeight()) Main.tempGameOver();
                break;
            case LEFT:
                if (headPosX-1 < 0) Main.tempGameOver();
                break;
            case RIGHT:
                if (headPosX+1 < Grid.getWidth()) Main.tempGameOver();
        }
    }
}