package MISC;

import Menu.Screen;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomDialogsSetting {

    public CustomDialogsSetting(Stage stage, Stage owner, VBox box)
    {
        this(stage,owner,box,false);
    }

    public CustomDialogsSetting(Stage stage, Stage owner, VBox box, boolean colorfill)
    {
        if(!colorfill)
        {
            BackgroundImage b = new BackgroundImage(new Image("Images/loginSignUpBack.jpg", Screen.getWidth(), Screen.getFullScreenHeight(),true,false), BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            box.setBackground(new Background(b));
        }
        else box.setBackground(new Background(new BackgroundFill(Color.rgb(108,187,17),CornerRadii.EMPTY, Insets.EMPTY)));
        box.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //box.prefWidthProperty().bind(stage.widthProperty().multiply(0.80));
        stage.initOwner(owner);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();

    }
}
