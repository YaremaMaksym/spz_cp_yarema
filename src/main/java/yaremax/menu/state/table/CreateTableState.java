package yaremax.menu.state.table;

import yaremax.jdbc.TableManager;
import yaremax.menu.MenuManager;
import yaremax.menu.state.MenuState;
import yaremax.model.Table;
import yaremax.util.input.InputHandler;
import yaremax.util.input.TableInputHandler;

import java.sql.SQLException;

public class CreateTableState implements MenuState {
    private final MenuManager menuManager;
    private final InputHandler inputHandler;
    private final TableManager tableManager;
    private final TableInputHandler tableInputHandler;

    public CreateTableState(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.tableManager = TableManager.getInstance();
        this.tableInputHandler = TableInputHandler.getInstance();
        this.inputHandler = InputHandler.getInstance();
    }

    @Override
    public void display() {
        System.out.println("""
                Створення нової таблиці:
                1️⃣ - Створити таблицю
                0️⃣ - Повернутися до головного меню
                """);
    }

    @Override
    public void handleInput() throws SQLException {
        int choice = inputHandler.inputInt("Введіть ваш вибір: ");
        switch (choice) {
            case 1 -> {
                Table table = tableInputHandler.inputTable();
                tableManager.createTable(table);
                System.out.println("Таблиця " + table.getName() + " створена.");
            }
            case 0 -> menuManager.setCurrentState(menuManager.getMainMenuState());
            default -> System.out.println("🛑🛑🛑 Опції " + choice + " немає в списку запропонованих 🛑🛑🛑");
        }
    }
}
