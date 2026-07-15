package logic;

import IO.DatabaseInterface;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Spieler {

    private final String name;
    private final int id;

    public Spieler(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Spieler(DatabaseInterface dbi, String name)  throws SQLException {
        String query = "SELECT id, name, passwort, spielerNr FROM Spieler WHERE name = '" + name + "'";
        ResultSet rs = dbi.executeQuery(query);

        if (rs != null && rs.next()) {
            this.id = rs.getInt("id");
            this.name = rs.getString("name");
        } else {
            throw new SQLException("Spieler mit Namen '" + name + "' wurde nicht gefunden!");
        }
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

    public void updateHighscore(DatabaseInterface dbi, int score, int spielID) throws SQLException {
            int aktuellerHighscore = getHighscore(dbi, spielID, this.id);

            if (score > aktuellerHighscore) {
                String query = "UPDATE Highscore SET highscore = " + score +
                        " WHERE spielerID = " + this.id +
                        " AND spielID = " + spielID;
                dbi.executeUpdate(query);
                System.out.println("Highscore aktualisiert auf: " + score);
            } else {
                System.out.println("Score " + score + " ist nicht höher als " + aktuellerHighscore + ". Kein Update nötig.");
            }
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
                    rs.getString("name"),
                    rs.getInt("id")
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
