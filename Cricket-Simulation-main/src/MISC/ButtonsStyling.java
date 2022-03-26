package MISC;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ButtonsStyling {

    public ButtonsStyling(Button b,int fontsize)
    {
        b.setTextFill(Color.WHITE);
        b.setFont(Font.font(fontsize));
        b.setStyle(ButtonsStyling.getIdleButtonStyle());
        b.setOnMouseEntered(e -> b.setStyle(getHoveredButtonStyle()));
        b.setOnMouseExited(e -> b.setStyle(getIdleButtonStyle()));
    }
    private final static String IDLE_BUTTON_STYLE =  "-fx-fill: white;\n"
            + "-fx-background-color: transparent;";
    private final static String HOVERED_BUTTON_STYLE = "-fx-border-color: white;\n"
            + "-fx-border-insets: 5;\n"
            + "-fx-border-width: 1;\n"
            + "-fx-border-style: dashed;\n"
            + "-fx-background-color: transparent;";

    public static String getIdleButtonStyle() {
        return IDLE_BUTTON_STYLE;
    }

    public static String getHoveredButtonStyle() {
        return HOVERED_BUTTON_STYLE;
    }
}
