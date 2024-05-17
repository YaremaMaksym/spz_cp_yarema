package yaremax.model;

import java.util.HashMap;
import java.util.Map;

public class Row {
    private Map<String, Object> values;

    public Row() {
        this.values = new HashMap<>();
    }

    public void setValue(String columnName, Object value, DataType dataType) throws IllegalArgumentException {
        if (dataType.isValidValue(value)) {
            values.put(columnName, value);
        }
        else throw new IllegalArgumentException("Invalid value for column: " + columnName);
    }

    public Object getValue(String columnName) {
        return values.get(columnName);
    }

    public String formatValue(String columnName, DataType dataType) {
        Object value = values.get(columnName);
        if (value != null) {
            return dataType.formatValue(value);
        }
        return "NULL";
    }
}
