package yaremax.jdbc;

import yaremax.model.Column;
import yaremax.model.Row;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

public class RowManager {
    private DBConnectionManager dbConnectionManager;

    public RowManager() {
        this.dbConnectionManager = DBConnectionManager.getInstance();
    }

    public void insertRow(String tableName, Row row) throws SQLException {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " ( ");

        Iterator<String> columnNameIterator = row.getValues().keySet().stream().map(Column::getName).iterator();
        while (columnNameIterator.hasNext()) {
            query.append(columnNameIterator.next());
            if (columnNameIterator.hasNext()) query.append(", ");
        }

        query.append(" ) VALUES ( ");

        Iterator<Column> columnIterator = row.getValues().keySet().iterator();
        while (columnIterator.hasNext()) {
            query.append(row.getFormatedValue(columnIterator.next()));
            if (columnIterator.hasNext()) query.append(", ");
        }

        query.append(" ) ");

        try (Connection connection = dbConnectionManager.openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            preparedStatement.executeUpdate();
        }
    }

    public void updateRow(String tableName, Row row, String condition) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE " + tableName + " SET ");

        Iterator<Column> columnIterator = row.getValues().keySet().iterator();
        while (columnIterator.hasNext()) {
            Column column = columnIterator.next();
            query.append(column.getName()).append(" = ").append(row.getFormatedValue(column));
            if (columnIterator.hasNext()) query.append(", ");
        }

        query.append(" WHERE ").append(condition);

        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
            preparedStatement.executeUpdate();
        }
    }

    public void deleteRow(String tableName, String condition) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + condition;

        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
    }
}
