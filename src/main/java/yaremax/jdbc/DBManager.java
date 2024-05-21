package yaremax.jdbc;

import java.sql.*;

public class DBManager {
    private DBConnectionManager dbConnectionManager;
    private static DBManager instance;

    private DBManager() {
        dbConnectionManager = DBConnectionManager.getInstance();
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public void executeCustomQuery(String sql) {
        try (Connection connection = DBConnectionManager.getInstance().openConnection();
             Statement statement = connection.createStatement()) {

            boolean isResultSet = statement.execute(sql);

            if (isResultSet) {
                // returns ResultSet (ex. SELECT)
                try (ResultSet resultSet = statement.getResultSet()) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(metaData.getColumnName(i) + "\t");
                    }
                    System.out.println();

                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(resultSet.getString(i) + "\t");
                        }
                        System.out.println();
                    }
                }
            } else {
                // doesn't return ResultSet (ex. UPDATE, DELETE)
                int affectedRows = statement.getUpdateCount();
                System.out.println("Affected rows: " + affectedRows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
