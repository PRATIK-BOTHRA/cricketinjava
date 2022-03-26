package Menu;


import MISC.ButtonsStyling;
import MISC.StageDisplay;
import MatchSimulation.Cricket;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameSelect {
    private Button startmatch,back;
    private GridPane layout;
    private Stage stage;

    public GameSelect(){
        startmatch = new Button("START!");
        back = new Button("BACK!");

        layout=new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(30);
        layout.setHgap(10);

        int i=0;
        for(; i<Screen.getSceneWidth(); i+=Screen.getSceneWidth()/3) layout.getColumnConstraints().add(new ColumnConstraints(Screen.getSceneWidth()/3));
        int k=0;
        for(; k<Screen.getFullScreenHeight(); k+=90) layout.getRowConstraints().add(new RowConstraints(90));
        i/=300;
        k/=90;
        k+=3;
        layout.setConstraints(startmatch,1,k/2 - 7);
        layout.setConstraints(back,1,k/2 - 6);

        new ButtonsStyling(startmatch,40);
        new ButtonsStyling(back,40);

        startmatch.setTextFill(Color.BLACK);
        back.setTextFill(Color.BLACK);

        startmatch.setOnAction(event -> {
            Cricket.start();
        });

        layout.getChildren().addAll(startmatch,back);

        back.setOnAction(event -> {
            main_menu.getMain().showMainMenu();
        });

        BackgroundImage b = new BackgroundImage(new Image("Images/gameSBack.jpg", Screen.getWidth(), Screen.getFullScreenHeight(),true,false), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        layout.setBackground(new Background(b));

        stage = main_menu.getMain().getStage();
        new StageDisplay(layout);
    }
}
