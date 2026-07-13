package IO;

import processing.core.PApplet;
import processing.core.PConstants;

public class Button {

    private final int X;
    private final int Y;

    private final int WIDTH;
    private final int HEIGHT;

    private final String TEXT;


    public Button(int x, int y, int width, int height, String text) {
        this.X = x;
        this.Y = y;

        this.WIDTH = width;
        this.HEIGHT = height;

        this.TEXT = text;
    }

    void execute(PApplet app) {
        drawSelf(app);
        if (checkPressed(app)) executeFunction();
    }

    void drawSelf(PApplet app) {
        app.fill(50);
        app.rect(X, Y, WIDTH, HEIGHT, 10);
        app.fill(255);
        app.textSize(20);
        app.textAlign(PConstants.CENTER, PConstants.CENTER);
        app.text(TEXT, X + (WIDTH/2), Y +  (HEIGHT/2));
    }

    boolean checkPressed(PApplet app) {
        return app.mousePressed && (app.mouseX >= X && app.mouseX <= X + WIDTH && app.mouseY >= Y && app.mouseY <= Y + HEIGHT);
    }

    void executeFunction() {
    }
}