package yaremax.jdbc;

import org.junit.jupiter.api.*;
import yaremax.model.Column;
import yaremax.model.DataType;
import yaremax.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.Random.class)
class TableManagerTest {
    private static final String TEST_TABLE_NAME = "test_table";

    private TableManager tableManager;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        tableManager = new TableManager();
        connection = DBConnectionManager.getInstance().openConnection();
    }

    @AfterEach
    void tearDown() {
        tableManager.dropTable(TEST_TABLE_NAME);
    }

    @Test
    void createTable() throws SQLException {
        // Arrange
        Column column1 = new Column("id", DataType.INTEGER);
        Column column2 = new Column("name", DataType.TEXT);
        Set<Column> columns = new HashSet<>();
        columns.add(column1);
        columns.add(column2);
        Table table = new Table(TEST_TABLE_NAME, columns);

        // Act
        tableManager.createTable(table);

        // Assert
        // Check if the table exists
        PreparedStatement statement = connection.prepareStatement("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ?)");
        statement.setString(1, TEST_TABLE_NAME);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        assertTrue(resultSet.getBoolean(1), "Table " + TEST_TABLE_NAME + " does not exist");

        // Check if the table has the expected columns
        statement = connection.prepareStatement("SELECT column_name, data_type FROM information_schema.columns WHERE table_name = ?");
        statement.setString(1, TEST_TABLE_NAME);
        resultSet = statement.executeQuery();
        int actualColumnCount = 0;
        while (resultSet.next()) {
            String columnName = resultSet.getString("column_name");
            String dataType = resultSet.getString("data_type");
            if (columnName.equals("id")) {
                assertEquals("integer", dataType, "Incorrect data type for column 'id'");
                actualColumnCount++;
            } else if (columnName.equals("name")) {
                assertEquals("text", dataType, "Incorrect data type for column 'name'");
                actualColumnCount++;
            }
        }
        assertEquals(2, actualColumnCount, "Incorrect number of columns in table " + TEST_TABLE_NAME);
    }

    @Test
    void alterTable() throws SQLException {
        // Arrange
        Column column1 = new Column("id", DataType.INTEGER);
        Column column2 = new Column("name", DataType.TEXT);
        Column column3 = new Column("age", DataType.INTEGER);
        Column column4 = new Column("email", DataType.TEXT);
        Set<Column> initialColumns = new HashSet<>();
        initialColumns.add(column1);
        initialColumns.add(column2);
        Table table = new Table(TEST_TABLE_NAME, initialColumns);
        tableManager.createTable(table);

        List<Column> addedColumns = new ArrayList<>();
        addedColumns.add(column3);
        addedColumns.add(column4);
        List<Column> removedColumns = new ArrayList<>();
        removedColumns.add(column2);

        // Act
        tableManager.alterTable(table, addedColumns, removedColumns);

        // Assert
        Set<Column> expectedColumns = new HashSet<>();
        expectedColumns.add(column1);
        expectedColumns.add(column3);
        expectedColumns.add(column4);
        assertEquals(expectedColumns, tableManager.getColumnsForTable(TEST_TABLE_NAME));
    }

    @Test
    void dropTable() throws SQLException {
        // Arrange
        Column column1 = new Column("id", DataType.INTEGER);
        Column column2 = new Column("name", DataType.TEXT);
        Set<Column> columns = new HashSet<>();
        columns.add(column1);
        columns.add(column2);
        Table table = new Table(TEST_TABLE_NAME, columns);
        tableManager.createTable(table);

        // Act
        tableManager.dropTable(TEST_TABLE_NAME);

        // Assert
        PreparedStatement statement = connection.prepareStatement("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ?)");
        statement.setString(1, TEST_TABLE_NAME);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        assertFalse(resultSet.getBoolean(1), "Table " + TEST_TABLE_NAME + " still exists");
    }

    @Test
    void getColumnsForTable() throws SQLException {
        // Arrange
        Column column1 = new Column("id", DataType.INTEGER);
        Column column2 = new Column("name", DataType.TEXT);
        Column column3 = new Column("age", DataType.BIGINT);
        Set<Column> columns = new HashSet<>();
        columns.add(column1);
        columns.add(column2);
        columns.add(column3);
        Table table = new Table(TEST_TABLE_NAME, columns);
        tableManager.createTable(table);

        // Act
        Set<Column> actualColumns = tableManager.getColumnsForTable(TEST_TABLE_NAME);

        // Assert
        assertEquals(columns, actualColumns);
    }
}