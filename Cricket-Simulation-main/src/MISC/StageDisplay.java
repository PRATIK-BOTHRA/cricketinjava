package MISC;

import Menu.main_menu;
import javafx.scene.Parent;

public class StageDisplay {
    public StageDisplay(Parent layout)
    {
        main_menu.getMain().getStage().getScene().setRoot(layout);
        main_menu.getMain().getStage().setTitle("Cricket Simulator");
    }
}
