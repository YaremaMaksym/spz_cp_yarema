package yaremax.util.input;

import yaremax.model.Column;
import yaremax.model.DataType;

import java.util.HashSet;
import java.util.Set;

public class ColumnInputHandler {
    private final InputHandler inputHandler;
    private static ColumnInputHandler instance;

    private ColumnInputHandler() {
        this.inputHandler = InputHandler.getInstance();
    }

    public static synchronized ColumnInputHandler getInstance() {
        if (instance == null) {
            instance = new ColumnInputHandler();
        }
        return instance;
    }

    public Set<Column> inputMultipleColumns() {
        int numColumns = inputHandler.inputInt("Введіть кількість стовпців: ");

        Set<Column> columns = new HashSet<>();
        for (int i = 0; i < numColumns; i++) {
            String columnName = inputHandler.inputString("Введіть назву стовпця: ");
            String dataTypeStr = inputHandler.inputString("Введіть тип даних стовпця (" + DataType.getDataTypesAsString() + ")\nТип: ");
            DataType dataType = DataType.valueOf(dataTypeStr.toUpperCase());

            Column column = new Column(columnName, dataType);
            columns.add(column);
        }
        return columns;
    }

    public Column inputColumn() {
        String columnName = inputHandler.inputString("Введіть назву стовпця: ");
        String dataTypeStr = inputHandler.inputString("Введіть тип даних стовпця (" + DataType.getDataTypesAsString() + ")\nТип: ");
        DataType dataType = DataType.valueOf(dataTypeStr.toUpperCase());

        return new Column(columnName, dataType);
    }
}
