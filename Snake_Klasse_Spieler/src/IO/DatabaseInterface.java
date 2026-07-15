package IO;

import java.sql.*;

public class DatabaseInterface {
    String url;
    String user;
    String pass;

    Connection connection = null;


    public DatabaseInterface(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public void establishConnection() {
        try{
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ResultSet executeQuery(String query) throws SQLException{
        if (connection == null){
            System.out.println("Keine Verbindung");
        }
        try{
            Statement stm = connection.createStatement();
            return stm.executeQuery(query);
        } catch (SQLException e){
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void executeUpdate(String query) throws SQLException {
        if (connection == null) {
            throw new SQLException("Keine Verbindung zur Datenbank!");
        }
        try {
            Statement stm = connection.createStatement();
            stm.executeUpdate(query);
            System.out.println("UPDATE erfolgreich ausgeführt");
        } catch (SQLException e) {
            System.out.println("Fehler beim UPDATE: " + e.getMessage());
            throw e;
        }
    }

    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}