package MISC;

import Menu.Screen;
import Menu.main_menu;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WelcomeAboard {
    public WelcomeAboard(Stage owner,String name)
    {
        this(owner,name,false);
    }

    public WelcomeAboard(Stage owner,String name, boolean register)
    {
        ImageView img = new ImageView(new Image("Images/welcome.png",150,150,true,false));
        Label label=new Label("Welcome aboard!\n\t"+ name+" have a nice time.");
        label.setTextFill(Color.WHITE);
        Button ok=new Button("Thanks!");
        new ButtonsStyling(ok,20);
        VBox layout2=new VBox();
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().addAll(label,img,ok);
        Stage stage=new Stage();
        stage.setTitle("LOGGED IN!");
        ok.setOnAction(event -> {
            stage.close();
            if(register)
                main_menu.getMain().SignIn();
            else
                main_menu.getMain().showMainMenu();
        });
        Scene scene =new Scene(layout2,Screen.getNotiWidth(),Screen.getNotiHeight());
        stage.setScene(scene);
        new CustomDialogsSetting(stage,owner,layout2);
        ok.requestFocus();
    }
}
