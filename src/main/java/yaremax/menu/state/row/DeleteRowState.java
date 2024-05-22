package yaremax.menu.state.row;

import yaremax.jdbc.RowManager;
import yaremax.jdbc.TableManager;
import yaremax.menu.MenuManager;
import yaremax.menu.state.MenuState;
import yaremax.util.input.InputHandler;

import java.sql.SQLException;

public class DeleteRowState implements MenuState {
    private final MenuManager menuManager;
    private final InputHandler inputHandler;
    private final RowManager rowManager;

    public DeleteRowState(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.inputHandler = InputHandler.getInstance();
        this.rowManager = RowManager.getInstance();
    }

    @Override
    public void display() {
        System.out.println("""
                Видалення даних з таблиці:
                1️⃣ - Видалити дані з таблиці
                0️⃣ - Повернутися до головного меню
                """);
    }

    @Override
    public void handleInput() throws SQLException {
        int choice = inputHandler.inputInt("Введіть ваш вибір: ");
        switch (choice) {
            case 1 -> {
                String tableName = inputHandler.inputString("Введіть назву таблиці: ");
                String condition = inputHandler.inputString("Введіть умову для видалення колонок (SQL) напр. \"id = 2\": ");
                rowManager.deleteRow(tableName, condition);
                System.out.println("Дані оновлені у таблиці " + tableName + ".");
            }
            case 0 -> menuManager.setCurrentState(menuManager.getMainMenuState());
            default -> System.out.println("🛑🛑🛑 Опції " + choice + " немає в списку запропонованих 🛑🛑🛑");
        }
    }
}
