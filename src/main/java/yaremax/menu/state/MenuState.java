package yaremax.menu.state;

import java.sql.SQLException;

public interface MenuState {
    void display();
    void handleInput() throws SQLException;
}
