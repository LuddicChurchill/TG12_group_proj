package logic;

import IO.DatabaseInterface;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Spieler {

    private int spielernr;
    private String name;
    private int id;
    private String passwort;


    Spieler(int spielernr, String passwort, String name, int id) {
        this.spielernr = spielernr;
        this.passwort = passwort;
        this.name = name;
        this.id = id;
    }

    public static boolean login(DatabaseInterface dbi, String name, String passwort) throws SQLException {
        String query = "SELECT passwort FROM spieler WHERE benutzername = " + name;
        ResultSet rs = dbi.executeQuery(query);

        if (rs.next()) { // Benutzer gefunden
            String gespeichertesPasswort = rs.getString("passwort");
            return gespeichertesPasswort.equals(passwort);
        }
        else {
        return false; // Benutzer nicht gefunden oder Fehler
    }


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
}