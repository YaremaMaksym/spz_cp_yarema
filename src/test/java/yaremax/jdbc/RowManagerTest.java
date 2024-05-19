package yaremax.jdbc;

import org.junit.jupiter.api.*;
import yaremax.model.Column;
import yaremax.model.DataType;
import yaremax.model.Row;
import yaremax.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.Random.class)
class RowManagerTest {
    private static final String TEST_TABLE_NAME = "test_table";

    private RowManager rowManager;
    private TableManager tableManager;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        rowManager = new RowManager();
        tableManager = new TableManager();
        connection = DBConnectionManager.getInstance().openConnection();

        Column column1 = new Column("id", DataType.INTEGER);
        Column column2 = new Column("name", DataType.TEXT);
        Column column3 = new Column("age", DataType.INTEGER);
        Set<Column> columns = new HashSet<>();
        columns.add(column1);
        columns.add(column2);
        columns.add(column3);
        Table table = new Table(TEST_TABLE_NAME, columns);
        tableManager.createTable(table);
    }

    @AfterEach
    void tearDown() throws SQLException {
        tableManager.dropTable(TEST_TABLE_NAME);
    }

    @Test
    void insertRow() throws SQLException {
        // Arrange
        Row row = new Row();
        row.setValue(new Column("id", DataType.INTEGER), 1);
        row.setValue(new Column("name", DataType.TEXT), "John Doe");
        row.setValue(new Column("age", DataType.INTEGER), 30);

        // Act
        rowManager.insertRow(TEST_TABLE_NAME, row);

        // Assert
        String query = "SELECT id, name, age FROM " + TEST_TABLE_NAME;
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        assertThat(resultSet.getInt("id")).isEqualTo(1);
        assertThat(resultSet.getString("name")).isEqualTo("John Doe");
        assertThat(resultSet.getInt("age")).isEqualTo(30);
        assertThat(resultSet.next()).isFalse();
    }

    @Test
    void updateRow() throws SQLException {
        // Arrange
        Row rowToInsert = new Row();
        rowToInsert.setValue(new Column("id", DataType.INTEGER), 1);
        rowToInsert.setValue(new Column("name", DataType.TEXT), "John Doe");
        rowToInsert.setValue(new Column("age", DataType.INTEGER), 30);
        rowManager.insertRow(TEST_TABLE_NAME, rowToInsert);

        Row rowToUpdate = new Row();
        rowToUpdate.setValue(new Column("name", DataType.TEXT), "Jane Smith");
        rowToUpdate.setValue(new Column("age", DataType.INTEGER), 35);

        // Act
        rowManager.updateRow(TEST_TABLE_NAME, rowToUpdate, "id = 1");

        // Assert
        String query = "SELECT id, name, age FROM " + TEST_TABLE_NAME;
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        assertThat(resultSet.getInt("id")).isEqualTo(1);
        assertThat(resultSet.getString("name")).isEqualTo("Jane Smith");
        assertThat(resultSet.getInt("age")).isEqualTo(35);
        assertThat(resultSet.next()).isFalse();
    }

    @Test
    void deleteRow() throws SQLException {
        // Arrange
        Row row = new Row();
        row.setValue(new Column("id", DataType.INTEGER), 1);
        row.setValue(new Column("name", DataType.TEXT), "John Doe");
        row.setValue(new Column("age", DataType.INTEGER), 30);
        rowManager.insertRow(TEST_TABLE_NAME, row);

        // Act
        rowManager.deleteRow(TEST_TABLE_NAME, "id = 1");

        // Assert
        String query = "SELECT id, name, age FROM " + TEST_TABLE_NAME;
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        assertThat(resultSet.next()).isFalse();
    }
}