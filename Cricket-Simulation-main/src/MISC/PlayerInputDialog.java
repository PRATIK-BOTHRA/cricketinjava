package MISC;

import Menu.Screen;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PlayerInputDialog {

    private Stage owner;
    private String title;
    private String message;
    private String button;
    private TextField text;
    private Stage stage;
    private String textEntered;

    public PlayerInputDialog(Stage owner, String title, String message, String button)
    {

        this.owner=owner;
        this.title=title;
        this.message=message;
        this.button=button;
        text = new TextField();
        text.setPromptText("Overs");
        settingUP();
    }

    public void settingUP()
    {
        Label label=new Label(message);
        label.setTextFill(Color.WHITE);
        Button ok=new Button(button);
        new ButtonsStyling(ok,20);
        VBox layout2=new VBox();
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().addAll(label,text,ok);
        stage=new Stage();
        stage.setTitle(title);
        ok.setOnAction(event -> {
            event();
        });
        text.setOnAction(event -> event());
        Scene scene =new Scene(layout2, Screen.getNotiWidth(),Screen.getNotiHeight());
        stage.setScene(scene);
        new CustomDialogsSetting(stage,owner,layout2,true);
        ok.requestFocus();
    }

    private void event()
    {
        if(text.getText().length() < 1)
        {
            return;
        }
        textEntered = text.getText();
        stage.close();
    }

    public String getText() {
        return textEntered;
    }
}
