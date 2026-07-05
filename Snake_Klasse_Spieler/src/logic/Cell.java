package logic;

public class Cell {
    public enum states{EMPTY, HEAD, BODY, APPLE}
    private states content;

    public Cell(states content) {
        this.content = content;
    }

    public boolean isEmpty(){
        return content == states.EMPTY;
    }

    public states getContent() {
        return content;
    }

    public void setContent(states content) {
        this.content = content;
    }
}