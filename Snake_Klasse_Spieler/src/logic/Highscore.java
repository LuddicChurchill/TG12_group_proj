package logic;

public class Highscore {

    private int spielerID;
    private int spielID;
    private int highscore;

    public Highscore(int spielerID, int spielID, int highscore) {
        this.spielerID = spielerID;
        this.spielID = spielID;
        this.highscore = highscore;
    }


    public int getSpielerID() {
        return spielerID;
    }

    public int getSpielID() {
        return spielID;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }


    @Override
    public String toString() {
        return "Highscore{" + "spielerID=" + spielerID + ", spielID=" + spielID + ", highscore=" + highscore + '}';
        }
    }

