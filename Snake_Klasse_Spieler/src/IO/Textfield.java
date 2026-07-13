package IO;

import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class Textfield extends Button{
    static Textfield active;
    ArrayList<Character> writtenInput = new ArrayList<>();


    public Textfield(int x, int y, int width, int height) {
        super(x, y, width, height, "");
    }

    @Override
    void execute(PApplet app) {
        drawSelf(app);
        if(checkPressed(app)) active = this;
    }

    @Override
    void drawSelf(PApplet app) {
        app.strokeWeight(2);
        if(active == this) app.stroke(100, 190, 255);
        else app.stroke(30);
        app.fill(255);
        app.rect(X, Y, WIDTH,  HEIGHT, 5);
        app.noStroke();
        app.fill(0);
        app.textSize(20);
        app.textAlign(PConstants.LEFT, PConstants.CENTER);
        app.text(writtenInputToString(), X + 3, Y +  (HEIGHT/2));
    }

    void selectNext() {}

    String writtenInputToString() {
        String result = "";
        for(int i = 0; i < writtenInput.size() - 1; i++){
            result += writtenInput.get(i);
        }
        return result;
    }
}