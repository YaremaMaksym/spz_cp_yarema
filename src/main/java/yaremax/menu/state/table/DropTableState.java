package yaremax.menu.state.table;

import yaremax.jdbc.TableManager;
import yaremax.menu.MenuManager;
import yaremax.menu.state.MenuState;
import yaremax.util.input.InputHandler;

public class DropTableState implements MenuState {
    private final MenuManager menuManager;
    private final InputHandler inputHandler;
    private final TableManager tableManager;

    public DropTableState(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.inputHandler = InputHandler.getInstance();
        this.tableManager = TableManager.getInstance();
    }

    @Override
    public void display() {
        System.out.println("""
                Видалення таблиці:
                1️⃣ - Видалити таблицю
                0️⃣ - Повернутися до головного меню
                """);
    }

    @Override
    public void handleInput() {
        int choice = inputHandler.inputInt("Введіть ваш вибір: ");
        switch (choice) {
            case 1 -> {
                String tableName = inputHandler.inputString("Введіть назву таблиці для видалення: ");
                tableManager.dropTable(tableName);
                System.out.println("Таблиця " + tableName + " видалена.");
            }
            case 0 -> menuManager.setCurrentState(menuManager.getMainMenuState());
            default -> System.out.println("🛑🛑🛑 Опції " + choice + " немає в списку запропонованих 🛑🛑🛑");
        }
    }
}
