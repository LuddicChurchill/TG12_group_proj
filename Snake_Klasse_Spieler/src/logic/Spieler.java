package logic;

import IO.DatabaseInterface;

import java.awt.*;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Spieler {

    private int spielernr;
    private String name;
    private int id;
    private String passwort;
    private int highscore;


    Spieler(int spielernr, String passwort, String name, int id, int highscore) {
        this.spielernr = spielernr;
        this.passwort = passwort;
        this.name = name;
        this.id = id;
        this.highscore = highscore;
    }

    public static boolean login(DatabaseInterface dbi, String name, String passwort) throws SQLException {
        String query = "SELECT passwort FROM spieler WHERE name = " + name;
        ResultSet rs = dbi.executeQuery(query);

        if (rs.next()) { // Benutzer gefunden
            String gespeichertesPasswort = rs.getString("passwort");
            return gespeichertesPasswort.equals(passwort);
        }
        else {
        return false; // Benutzer nicht gefunden oder Fehler
    }


    }

    public int getHighscore(DatabaseInterface dbi,int spielID, int spielerID) throws SQLException {
        String query = "SELECT highscore FROM score WHERE spielID = "
                + spielID + " AND spielerID = " + spielerID;

        ResultSet rs = dbi.executeQuery(query);

        try {
            if (rs.next()) {
                return rs.getInt("highscore");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Spieler[] getLeaderboard(DatabaseInterface dbi, int spielId, int amount) throws SQLException {
        Spieler[] leaderboard  = new Spieler[amount];
        String query = "SELECT s.id, s.name, s.passwort, s.spielerNr, h.highscore " + "FROM Highscore h " + "JOIN Spieler s ON h.spielerID = s.id " + "WHERE h.spielID = " + spielId + " ORDER BY h.highscore DESC " + " LIMIT " + amount;

        ResultSet rs = dbi.executeQuery(query);

        int i = 0;

        while (rs.next() && i < amount) {
            leaderboard[i] = new Spieler(
                    rs.getInt("spielerNr"),
                    rs.getString("passwort"),
                    rs.getString("name"),
                    rs.getInt("id"),
                    rs.getInt("highscore")
            );
            i++;
        }

        return leaderboard;
    }


    public String getRang(DatabaseInterface dbi,int spielId, String name, int amount){


    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getSpielernr() {
        return spielernr;
    }

    public void setSpielernr(int spielernr) {
        this.spielernr = spielernr;
    }
    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}