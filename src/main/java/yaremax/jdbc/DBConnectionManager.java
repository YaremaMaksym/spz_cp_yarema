package yaremax.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static DBConnectionManager instance;
    private static final String URL = "jdbc:postgresql://localhost:5432/dbms_app";
    private static final String USER = "postgres";
    private static final String PASSWORD = "pass";
    private Connection connection;

    private DBConnectionManager() {}

    public static synchronized DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    public Connection openConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection () {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Successfully disconnected from db");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
