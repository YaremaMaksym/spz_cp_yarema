package yaremax;

import yaremax.menu.MenuManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        MenuManager menuManager = new MenuManager();
        menuManager.run();
    }
}
