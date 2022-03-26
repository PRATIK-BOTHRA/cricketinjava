package MISC;

import Menu.Screen;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginRegisterErrors {
     public LoginRegisterErrors(Stage owner, String errormsg)
     {
         ImageView img = new ImageView(new Image("Images/crash.png",150,150,true,false));
         Label label=new Label(errormsg);
         label.setTextFill(Color.WHITE);
         Button ok=new Button("Try Again");
         new ButtonsStyling(ok,20);
         VBox layout2=new VBox();
         layout2.setAlignment(Pos.CENTER);
         layout2.getChildren().addAll(label,img,ok);

         Stage stage=new Stage();
         stage.setTitle("ERROR");
         ok.setOnAction(event -> {
             stage.close();
         });
         Scene scene =new Scene(layout2,Screen.getNotiWidth(),Screen.getNotiHeight());
         stage.setScene(scene);
         new CustomDialogsSetting(stage,owner,layout2);
         ok.requestFocus();
     }
}
