package yaremax.util.input;

import yaremax.model.Column;
import yaremax.model.Table;

import java.util.Set;

public class TableInputHandler {
    private final InputHandler inputHandler;
    private final ColumnInputHandler columnInputHandler;
    private static TableInputHandler instance;

    private TableInputHandler() {
        this.columnInputHandler = ColumnInputHandler.getInstance();
        this.inputHandler = InputHandler.getInstance();
    }

    public static synchronized TableInputHandler getInstance() {
        if (instance == null) {
            instance = new TableInputHandler();
        }
        return instance;
    }

    public Table inputTable() {
        String tableName = inputHandler.inputString("Введіть назву таблиці: ");
        System.out.println("Ввід колонок для створення таблиці...");
        Set<Column> columns = columnInputHandler.inputMultipleColumns();

        return new Table(tableName, columns);
    }

}
