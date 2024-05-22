package yaremax.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
    private static DBConnectionManager instance;
    private static final String URL = "jdbc:postgresql://localhost:5432/dbms_app";
    private static final String USER = "postgres";
    private static final String PASSWORD = "pass";

    private DBConnectionManager() {}

    public static synchronized DBConnectionManager getInstance() {
        if (instance == null) {
            instance = new DBConnectionManager();
        }
        return instance;
    }

    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
