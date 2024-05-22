package yaremax.menu.state;

import yaremax.jdbc.DBManager;
import yaremax.jdbc.TableManager;
import yaremax.menu.MenuManager;
import yaremax.model.Column;
import yaremax.model.Row;
import yaremax.model.Table;
import yaremax.util.TableFormatter;
import yaremax.util.input.InputHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class MainMenuState implements MenuState {
    private final DBManager dbManager;
    private final TableManager tableManager;
    private final MenuManager menuManager;
    private final InputHandler inputHandler;
    private final TableFormatter tableFormatter;

    public MainMenuState(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.inputHandler = InputHandler.getInstance();
        this.dbManager = DBManager.getInstance();
        this.tableManager = TableManager.getInstance();
        this.tableFormatter = TableFormatter.getInstance();
    }

    @Override
    public void display() {
        // example
        System.out.println("""
                \n
                Головне меню:
                1️⃣ - Створити таблицю
                2️⃣ - Редагувати таблицю
                3️⃣ - Видалити таблицю
                4️⃣ - Додати дані до таблиці
                5️⃣ - Редагувати дані у таблиці
                6️⃣ - Видалити дані з таблиці
                7️⃣ - Виконати користувацький запит
                8️⃣ - Отримати дані
                9️⃣ - Завершити роботу
                """);
    }

    @Override
    public void handleInput() throws SQLException {
        int choice = inputHandler.inputInt("Введіть ваш вибір: ");

        switch (choice) {
            case 1 -> menuManager.setCurrentState(menuManager.getCreateTableState());
            case 2 -> menuManager.setCurrentState(menuManager.getAlterTableState());
            case 3 -> menuManager.setCurrentState(menuManager.getDropTableState());
            case 4 -> menuManager.setCurrentState(menuManager.getInsertRowState());
            case 5 -> menuManager.setCurrentState(menuManager.getUpdateRowState());
            case 6 -> menuManager.setCurrentState(menuManager.getDeleteRowState());
            case 7 -> {
                String sql = inputHandler.inputString("Введіть SQL запит, напр. \"SELECT * FROM users;\": ");
                dbManager.executeCustomQuery(sql);
                System.out.println("Запит успішно виконано");
            }
            case 8 -> {
                List<String> tableNames = tableManager.fetchTableNames();
                System.out.println("Існуючі таблиці: " + tableNames);
                String tableName = inputHandler.inputString("Введіть назву таблиці з якої потрібно отримати данні: ");
                Set<Column> columns = tableManager.getColumnsForTable(tableName);
                Table table = new Table(tableName, columns);

                List<Row> rows = tableManager.fetchRows(table);

                tableFormatter.formatAndPrintTable(table, rows);
            }
            case 9 -> menuManager.setCurrentState(menuManager.getExitState());
            default -> System.out.println("🛑🛑🛑 Опції " + choice + " немає в списку запропонованих 🛑🛑🛑");
        }
    }
}
