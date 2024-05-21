package yaremax.util;

import yaremax.model.Column;
import yaremax.model.Row;
import yaremax.model.Table;

import java.util.ArrayList;
import java.util.List;

public class TableFormatter {
    private static TableFormatter instance = new TableFormatter();

    private TableFormatter() {}

    public static TableFormatter getInstance() {
        if (instance == null) {
            instance = new TableFormatter();
        }
        return instance;
    }

    public void formatAndPrintTable(Table table, List<Row> rows) {
        List<Integer> maxColumnWidths = getMaxColumnWidths(table, rows);
        String headerFormat = getHeaderFormat(maxColumnWidths);
        String rowFormat = getRowFormat(maxColumnWidths);
        String divider = getDivider(maxColumnWidths);

        System.out.println("\n" + table.getName() + ": ");
        System.out.println(divider);
        printHeader(table, headerFormat);
        System.out.println(divider);

        for (Row row : rows) {
            printRow(table, row, rowFormat);
        }

        System.out.println(divider);
    }

    private String getRepeatedString(String str, int count) {
        return str.repeat(count);
    }

    private List<Integer> getMaxColumnWidths(Table table, List<Row> rows) {
        List<Integer> maxColumnWidths = new ArrayList<>();
        for (Column column : table.getColumns()) {
            int maxColumnWidth = column.getName().length();
            for (Row row : rows) {
                maxColumnWidth = Math.max(maxColumnWidth, row.getFormatedValue(column).length());
            }
            maxColumnWidths.add(maxColumnWidth);
        }
        return maxColumnWidths;
    }

    private String getHeaderFormat(List<Integer> maxColumnWidths) {
        StringBuilder headerFormatBuilder = new StringBuilder("| ");
        for (Integer width : maxColumnWidths) {
            headerFormatBuilder.append("%-").append(width).append("s | ");
        }
        return headerFormatBuilder.toString();
    }

    private String getRowFormat(List<Integer> maxColumnWidths) {
        StringBuilder rowFormatBuilder = new StringBuilder("| ");
        for (Integer width : maxColumnWidths) {
            rowFormatBuilder.append("%-").append(width).append("s | ");
        }
        return rowFormatBuilder.toString();
    }

    private String getDivider(List<Integer> maxColumnWidths) {
        StringBuilder dividerBuilder = new StringBuilder("+");
        for (Integer width : maxColumnWidths) {
            dividerBuilder.append(getRepeatedString("-", width + 2)).append("+");
        }
        return dividerBuilder.toString();
    }

    private void printHeader(Table table, String headerFormat) {
        List<String> headerValues = new ArrayList<>();
        for (Column column : table.getColumns()) {
            headerValues.add(column.getName());
        }
        System.out.printf(headerFormat, headerValues.toArray());
        System.out.print("\n");
    }

    private void printRow(Table table, Row row, String rowFormat) {
        List<String> rowValues = new ArrayList<>();
        for (Column column : table.getColumns()) {
            rowValues.add(row.getFormatedValue(column));
        }
        System.out.printf(rowFormat, rowValues.toArray());
        System.out.print("\n");
    }
}
