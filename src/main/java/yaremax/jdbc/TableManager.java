package yaremax.jdbc;

import yaremax.model.Column;
import yaremax.model.DataType;
import yaremax.model.Row;
import yaremax.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TableManager {
    private DBConnectionManager dbConnectionManager;
    private static TableManager instance;

    private TableManager() {
        this.dbConnectionManager = DBConnectionManager.getInstance();
    }

    public static TableManager getInstance() {
        if (instance == null) {
            instance = new TableManager();
        }
        return instance;
    }

    public void createTable(Table table) throws SQLException {
        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + table.getName() + " (");

        Iterator<Column> columnsIterator = table.getColumns().iterator();
        if (columnsIterator.hasNext()) {
            Column column = columnsIterator.next();
            query.append(column.getName()).append(" ").append(column.getDataType().toString());
        }
        while (columnsIterator.hasNext()) {
            Column column = columnsIterator.next();
            query.append(", ")
                    .append(column.getName())
                    .append(" ")
                    .append(column.getDataType().toString());
        }
        query.append(")");

        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            preparedStatement.executeUpdate();
        }
    }

    public void alterTable(Table table, List<Column> addedColumns, List<Column> removedColumns) throws SQLException {
        StringBuilder alterQuery = new StringBuilder("ALTER TABLE " + table.getName());

        // Process added columns
        if (!addedColumns.isEmpty()) {
            Iterator<Column> addedColumnsIterator = addedColumns.iterator();
            while (addedColumnsIterator.hasNext()) {
                Column column = addedColumnsIterator.next();
                alterQuery.append(" ADD COLUMN ").append(column.getName()).append(" ").append(column.getDataType().toString());
                if (addedColumnsIterator.hasNext()) {
                    alterQuery.append(",");
                }
            }
        }

        // Process removed columns
        if (!removedColumns.isEmpty()) {
            if (!addedColumns.isEmpty()) alterQuery.append(",");

            Iterator<Column> removedColumnsIterator = removedColumns.iterator();
            while (removedColumnsIterator.hasNext()) {
                Column column = removedColumnsIterator.next();
                alterQuery.append(" DROP COLUMN ").append(column.getName());
                if (removedColumnsIterator.hasNext()) {
                    alterQuery.append(",");
                }
            }
        }

        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(alterQuery.toString())) {
            preparedStatement.executeUpdate();
        }
    }

    public void dropTable(String tableName) {
        String query = "DROP TABLE IF EXISTS " + tableName;

        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Row> fetchRows(Table table) throws SQLException {
        List<Row> rows = new ArrayList<>();
        String query = "SELECT * FROM " + table.getName();

        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Row row = new Row();
                for (Column column : table.getColumns()) {
                    Object value = column.getDataType().parseFromString(resultSet.getString(column.getName()));
                    row.setValue(column, value);
                }
                rows.add(row);
            }
        }

        return rows;
    }

    public Set<Column> getColumnsForTable(String tableName) throws SQLException {
        Set<Column> columns = new HashSet<>();

        String query = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = ?";

        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tableName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String columnName = resultSet.getString("column_name");
                    String dataType = resultSet.getString("data_type");

                    columns.add(new Column(columnName, mapSqlTypeToDataType(dataType)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columns;
    }

    private DataType mapSqlTypeToDataType(String sqlType) {
        try {
            return DataType.valueOf(sqlType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported SQL data type: " + sqlType);
        }
    }

}
