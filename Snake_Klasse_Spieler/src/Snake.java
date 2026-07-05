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
        int headNewX;
        int headNewY;

        switch (heading) {
            case UP:
                if (headPosY - 1 < 0) Main.tempGameOver();
                headNewX = headPosX;
                headNewY = headPosY - 1;
                break;
            case DOWN:
                if (headPosY + 1 >= Grid.getHeight()) Main.tempGameOver();
                headNewX = headPosX;
                headNewY = headPosY + 1;
                break;
            case LEFT:
                if (headPosX - 1 < 0) Main.tempGameOver();
                headNewX = headPosX - 1;
                headNewY = headPosY;
                break;
            case RIGHT:
                if (headPosX + 1 < Grid.getWidth()) Main.tempGameOver();
                headNewX = headPosX + 1;
                headNewY = headPosY;
                break;
            default:
                throw new Exception("Invalid direction");
        }

        switch (Grid.grid[headNewX][headNewY].getContent()) {
            case BODY:
                Main.tempGameOver();
                break;
            case EMPTY:
                for (int i = bodyArrayX.size() - 1; i > 1; i--) {
                    bodyArrayY.set(i, bodyArrayX.get(i - 1));
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

                for (int i = bodyArrayX.size() - 2; i > 1; i--) {
                    bodyArrayX.set(i, bodyArrayX.get(i - 1));
                    bodyArrayY.set(i, bodyArrayY.get(i - 1));
                }
                bodyArrayX.set(0, headPosX);
                bodyArrayY.set(0, headPosY);

                headPosX = headNewX;
                headPosY = headNewY;
                break;
            default:
                System.out.println("how did we get here?");
        }
    }
}