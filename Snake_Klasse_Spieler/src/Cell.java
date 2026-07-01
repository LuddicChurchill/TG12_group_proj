public class Cell {
    public enum states{EMPTY, HEAD, BODY, APPLE};
    private states content = states.EMPTY;

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