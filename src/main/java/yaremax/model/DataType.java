package yaremax.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum DataType {
    INTEGER("INTEGER") {
        @Override
        public boolean isValidValue(Object value) {
            return value instanceof Integer;
        }

        @Override
        public String formatValue(Object value) {
            return value.toString();
        }
    },
    BIGINT("BIGINT") {
        @Override
        public boolean isValidValue(Object value) {
            return value instanceof Long;
        }

        @Override
        public String formatValue(Object value) {
            return value.toString();
        }
    },
    NUMERIC("NUMERIC") {
        @Override
        public boolean isValidValue(Object value) {
            return value instanceof BigDecimal;
        }

        @Override
        public String formatValue(Object value) {
            return value.toString();
        }
    },
    TEXT("TEXT") {
        @Override
        public boolean isValidValue(Object value) {
            return value instanceof String;
        }

        @Override
        public String formatValue(Object value) {
            return "'" + value.toString().replace("'", "''") + "'";
        }
    },
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
    };

    private final String sqlType;

    DataType(String sqlType) {
        this.sqlType = sqlType;
    }

    public abstract boolean isValidValue(Object value);

    public abstract String formatValue(Object value);
}
