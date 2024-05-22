package yaremax.util.input;

import yaremax.model.Column;
import yaremax.model.Row;
import yaremax.model.Table;

import java.util.Set;

public class RowInputHandler {
    private final InputHandler inputHandler;
    private static RowInputHandler instance;

    private RowInputHandler() {
        this.inputHandler = InputHandler.getInstance();
    }

    public synchronized static RowInputHandler getInstance() {
        if (instance == null) {
            instance = new RowInputHandler();
        }
        return instance;
    }

    public Row inputRow(Set<Column> columns) {
        Row row = new Row();

        for (Column column : columns) {
            String prompt = "Введіть значення для стовпця " + column.getName() + " (" + column.getDataType() + "): ";
            Object value = column.getDataType().parseFromString(inputHandler.inputString(prompt));
            row.setValue(column, value);
        }

        return row;
    }

}
