package yaremax.util.input;

import yaremax.model.Column;
import yaremax.model.DataType;
import yaremax.model.Table;

import java.util.HashSet;
import java.util.Set;

public class TableInputHandler {
    private final InputHandler inputHandler;
    private static TableInputHandler instance;

    private TableInputHandler() {
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
        int numColumns = inputHandler.inputInt("Введіть кількість стовпців: ");

        Set<Column> columns = new HashSet<>();
        for (int i = 0; i < numColumns; i++) {
            String columnName = inputHandler.inputString("Введіть назву стовпця: ");
            String dataTypeStr = inputHandler.inputString("Введіть тип даних стовпця (" + DataType.getDataTypesAsString() + ")\nТип: ");
            DataType dataType = DataType.valueOf(dataTypeStr.toUpperCase());

            Column column = new Column(columnName, dataType);
            columns.add(column);
        }

        return new Table(tableName, columns);
    }

}
