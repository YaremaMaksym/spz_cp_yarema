package yaremax.menu.state;

import yaremax.menu.MenuManager;

public class ExitState implements MenuState {
    private final MenuManager menuManager;

    public ExitState(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @Override
    public void display() {
        System.out.println("Завершення роботи...");
    }

    @Override
    public void handleInput() {

    }
}
