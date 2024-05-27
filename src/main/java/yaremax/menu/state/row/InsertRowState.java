package yaremax.menu.state.row;

import yaremax.jdbc.RowManager;
import yaremax.jdbc.TableManager;
import yaremax.menu.MenuManager;
import yaremax.menu.state.MenuState;
import yaremax.model.Column;
import yaremax.model.Row;
import yaremax.util.input.InputHandler;
import yaremax.util.input.RowInputHandler;

import java.sql.SQLException;
import java.util.Set;

public class InsertRowState implements MenuState {
    private final MenuManager menuManager;
    private final InputHandler inputHandler;
    private final RowInputHandler rowInputHandler;
    private final RowManager rowManager;
    private final TableManager tableManager;

    public InsertRowState(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.rowInputHandler = RowInputHandler.getInstance();
        this.inputHandler = InputHandler.getInstance();
        this.rowManager = RowManager.getInstance();
        this.tableManager = TableManager.getInstance();
    }

    @Override
    public void display() {
        System.out.println("""
                Додавання даних до таблиці:
                1️⃣ - Додати дані
                0️⃣ - Повернутися до головного меню
                """);
    }

    @Override
    public void handleInput() throws SQLException {
        int choice = inputHandler.inputInt("Введіть ваш вибір: ");
        switch (choice) {
            case 1 -> {
                String tableName = inputHandler.inputString("Введіть назву таблиці: ");
                Set<Column> columns = tableManager.getColumnsForTable(tableName);
                System.out.println("Ввід рядка для вставлення");
                Row row = rowInputHandler.inputRow(columns);
                rowManager.insertRow(tableName, row);
                System.out.println("Дані додані до таблиці " + tableName + ".");
            }
            case 0 -> menuManager.setCurrentState(menuManager.getMainMenuState());
            default -> System.out.println("🛑🛑🛑 Опції " + choice + " немає в списку запропонованих 🛑🛑🛑");
        }
    }
}
