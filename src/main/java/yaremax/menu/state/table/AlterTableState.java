package yaremax.menu.state.table;

import yaremax.jdbc.TableManager;
import yaremax.menu.MenuManager;
import yaremax.menu.state.MenuState;
import yaremax.model.Column;
import yaremax.model.Table;
import yaremax.util.input.ColumnInputHandler;
import yaremax.util.input.InputHandler;

import java.sql.SQLException;
import java.util.Set;

public class AlterTableState implements MenuState {
    private final TableManager tableManager;
    private final MenuManager menuManager;
    private final ColumnInputHandler columnInputHandler;
    private final InputHandler inputHandler;

    public AlterTableState(MenuManager menuManager) {
        this.columnInputHandler = ColumnInputHandler.getInstance();
        this.tableManager = TableManager.getInstance();
        this.menuManager = menuManager;
        this.inputHandler = InputHandler.getInstance();
    }

    @Override
    public void display() {
        System.out.println("""
                Редагування таблиці:
                1️⃣ - Редагувати таблицю
                0️⃣ - Повернутися до головного меню
                """);
    }

    @Override
    public void handleInput() throws SQLException {
        int choice = inputHandler.inputInt("Введіть ваш вибір: ");
        switch (choice) {
            case 1 -> {
                String tableName = inputHandler.inputString("Введіть назву таблиці для редагування: ");

                System.out.println("Ввід колонок для додавання до таблиці...");
                Set<Column> addedColumns = columnInputHandler.inputMultipleColumns();

                System.out.println("Ввід колонок для видалення з таблиці...");
                Set<Column> removedColumns = columnInputHandler.inputMultipleColumns();

                tableManager.alterTable(tableName, addedColumns, removedColumns);

                System.out.println("Таблиця " + tableName + " відредагована.");
            }
            case 0 -> menuManager.setCurrentState(menuManager.getMainMenuState());
            default -> System.out.println("🛑🛑🛑 Опції " + choice + " немає в списку запропонованих 🛑🛑🛑");
        }
    }
}
