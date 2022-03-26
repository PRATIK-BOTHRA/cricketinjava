package MISC;

import Menu.Screen;
import Player.Player;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CustomDialog {

    private Stage owner;
    private String title;
    private String message;
    private String button;
    private Player player;
    private Circle pattern;
    private boolean colorBack;


    public CustomDialog(Stage owner, String title, String message, String button, ImagePattern imgx)
    {
        Circle c = new Circle(50);
        c.setFill(imgx);
        this.owner=owner;
        this.title=title;
        this.message=message;
        this.button=button;
        settingUP((Node) c);
    }
    public CustomDialog(Stage owner, String title, String message, String button, String url)
    {
        this(owner,title,message,button,null,url,false);
    }
    public CustomDialog(Stage owner, String title, String message, String button, String url,boolean colorBack)
    {
        this(owner,title,message,button,null,url,colorBack);
    }
    public CustomDialog(Stage owner, String title, String message, String button, Player player, String url)
    {
        this(owner,title,message,button,player,url,false);
    }

    public CustomDialog(Stage owner, String title, String message, String button, Player player, String url,boolean colorBack)
    {
        ImageView img = new ImageView(new Image(url,150,150,true,false));
        this.owner=owner;
        this.title=title;
        this.message=message;
        this.button=button;
        this.player=player;
        this.colorBack=colorBack;
        settingUP((Node) img);
    }

    public void settingUP(Node n)
    {

        Label label=new Label(message);
        label.setTextFill(Color.WHITE);
        Button ok=new Button(button);
        new ButtonsStyling(ok,20);
        VBox layout2=new VBox();
        layout2.getChildren().add(new Label(" "));
        if(player != null)
        {
            pattern = new Circle(40,40, 40);
            pattern.setFill(player.getIcon());
            layout2.getChildren().add(pattern);
        }
        layout2.setAlignment(Pos.CENTER);
        layout2.getChildren().addAll(label,n,ok);
        Stage stage=new Stage();
        stage.setTitle(title);
        ok.setOnAction(event -> {
            stage.close();
        });
        Scene scene =new Scene(layout2,Screen.getNotiWidth(),Screen.getNotiHeight());
        stage.setScene(scene);
        new CustomDialogsSetting(stage,owner,layout2,colorBack);
        ok.requestFocus();
    }
}
