package logic;

import IO.DatabaseInterface;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Spieler {

    private int spielernr;
    private String name;
    private int id;
    private String passwort;
    private int highscore;

    public Spieler(int spielernr, String passwort, String name, int id, int highscore) {
        this.spielernr = spielernr;
        this.passwort = passwort;
        this.name = name;
        this.id = id;
        this.highscore = highscore;
    }

    public Spieler(DatabaseInterface dbi, String name) {
        //TODO: schreibe einen Constructor, der die Werte von Spieler
        // nicht als Parameter bekommt, sondern direkt aus der Datenbank holt
    }

    public static boolean login(DatabaseInterface dbi, String name, String passwort) throws SQLException {
        String query = "SELECT passwort FROM spieler WHERE name = '" + name + "'";
        ResultSet rs = dbi.executeQuery(query);

        if (rs != null && rs.next()) { // Benutzer gefunden
            String gespeichertesPasswort = rs.getString("passwort");
            return gespeichertesPasswort.equals(passwort);
        }
        return false; // Benutzer nicht gefunden oder Fehler
    }

    public void updateHighscore(int score, int spielID) {
        // TODO: schreibe eine Funktion, die den übergebnen Parameter score mit dem
        // aktuell in der Datenbank gespeicherten Highscore vergleicht.
        // Wenn der neue Score höher ist, muss die Database mit dem neuen Highscore
        // upgedatet werden
    }

    public static int getHighscore(DatabaseInterface dbi, int spielID, int spielerID) throws SQLException {
        String query = "SELECT highscore FROM Highscore WHERE spielID = " + spielID + " AND spielerID = " + spielerID;

        ResultSet rs = dbi.executeQuery(query);

        try {
            if (rs != null && rs.next()) {
                return rs.getInt("highscore");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Spieler[] getLeaderboard(DatabaseInterface dbi, int spielId, int amount) throws SQLException {
        Spieler[] leaderboard = new Spieler[amount];
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

    public static int getRang(DatabaseInterface dbi, int spielId, String name) throws SQLException {

        String query =
                "SELECT COUNT(*) + 1 AS rang " +
                        "FROM Highscore " +
                        "WHERE spielID = " + spielId +
                        " AND highscore > (" +
                        "SELECT highscore " +
                        "FROM Highscore h " +
                        "JOIN Spieler s ON h.spielerID = s.id " +
                        "WHERE h.spielID = " + spielId +
                        " AND s.name = '" + name + "')";

        ResultSet rs = dbi.executeQuery(query);

        if (rs.next()) {
            return rs.getInt("rang");
        }

        return -1;
    }
}
