package yaremax.menu;

import lombok.Getter;
import lombok.Setter;
import yaremax.menu.state.ExitState;
import yaremax.menu.state.MainMenuState;
import yaremax.menu.state.MenuState;
import yaremax.menu.state.row.DeleteRowState;
import yaremax.menu.state.row.InsertRowState;
import yaremax.menu.state.row.UpdateRowState;
import yaremax.menu.state.table.AlterTableState;
import yaremax.menu.state.table.CreateTableState;
import yaremax.menu.state.table.DropTableState;
import yaremax.util.input.InputHandler;

import java.sql.SQLException;

@Getter
@Setter
public class MenuManager {
    private MenuState currentState;
    private final MenuState createTableState;
    private final MenuState alterTableState;
    private final MenuState dropTableState;
    private final MenuState insertRowState;
    private final MenuState updateRowState;
    private final MenuState deleteRowState;
    private final MenuState mainMenuState;
    private final MenuState exitState;

    public MenuManager() {
        this.mainMenuState = new MainMenuState(this);
        this.exitState = new ExitState(this);
        this.createTableState = new CreateTableState(this);
        this.alterTableState = new AlterTableState(this);
        this.dropTableState = new DropTableState(this);
        this.insertRowState = new InsertRowState(this);
        this.updateRowState = new UpdateRowState(this);
        this.deleteRowState = new DeleteRowState(this);
        this.currentState = mainMenuState;
    }

    public void run() throws SQLException {
        while (true) {
            currentState.display();
            if (currentState instanceof ExitState) {
                break;
            }
            currentState.handleInput();
        }
    }
}
