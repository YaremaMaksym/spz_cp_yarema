package yaremax.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public enum DataType {
    INTEGER("INTEGER") {
        @Override
        public boolean isValidValue(Object value) {
            return value instanceof Integer;
        }

        @Override
        public String formatValueToSqlLiteral(Object value) {
            return value.toString();
        }

        @Override
        public Object parseFromString(String value) {
            return Integer.parseInt(value);
        }
    },
    BIGINT("BIGINT") {
        @Override
        public boolean isValidValue(Object value) {
            return value instanceof Long;
        }

        @Override
        public String formatValueToSqlLiteral(Object value) {
            return value.toString();
        }

        @Override
        public Object parseFromString(String value) {
            return Long.parseLong(value);
        }
    },
    TEXT("TEXT") {
        @Override
        public boolean isValidValue(Object value) {
            return value instanceof String;
        }

        @Override
        public String formatValueToSqlLiteral(Object value) {
            return "'" + value.toString().replace("'", "''") + "'";
        }

        @Override
        public Object parseFromString(String value) {
            return value;
        }
    };
    /*,
    BOOLEAN("BOOLEAN") {
        @Override
        public boolean isValidValue(Object value) {
            return value instanceof Boolean;
        }

        @Override
        public String formatValue(Object value) {
            return ((Boolean) value) ? "TRUE" : "FALSE";
        }
    },
    DATE("DATE") {
        @Override
        public boolean isValidValue(Object value) {
            return value instanceof java.sql.Date;
        }

        @Override
        public String formatValue(Object value) {
            return "'" + value.toString() + "'";
        }
    },
    TIMESTAMP("TIMESTAMP") {
        @Override
        public boolean isValidValue(Object value) {
            return value instanceof java.sql.Timestamp;
        }

        @Override
        public String formatValue(Object value) {
            return "'" + value.toString() + "'";
        }
    }*/

    private final String sqlType;

    DataType(String sqlType) {
        this.sqlType = sqlType;
    }

    public abstract boolean isValidValue(Object value);
    public abstract String formatValueToSqlLiteral(Object value);
    public abstract Object parseFromString(String value);

    public abstract String formatValue(Object value);
}
