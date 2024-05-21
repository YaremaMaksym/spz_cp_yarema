package yaremax.model;

import java.util.HashMap;
import java.util.Map;

public class Row {
    private Map<Column, Object> values;

    public Row() {
        this.values = new HashMap<>();
    }

    public void setValue(Column column, Object value) throws IllegalArgumentException {
        if (column.getDataType().isValidValue(value)) {
            values.put(column, value);
        }
        else throw new IllegalArgumentException("Invalid value for column: " + column.getName());
    }

    public Map<Column, Object> getValues() {
        return values;
    }

    public String getFormatedValue(Column column) {
        Object value = values.get(column);
        if (value != null) {
            return column.getDataType().formatValueToSqlLiteral(value);
        }
        return "NULL";
    }
}
