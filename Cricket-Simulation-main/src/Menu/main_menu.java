package Menu;

import MISC.SignInNotifiction;
import MISC.StageDisplay;
import Player.*;
import Player.SignIn;
import Player.StatsSaveLoad;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class main_menu extends Application{
    private Label newGame,signIn,stats,playerN,playerN2,exitL;

    private Group group;
    private Canvas canvas;
    private Stage stage;
    private Player player = null;
    private Player player2 = null;

    private Circle player1Icon;
    private Circle player2Icon;

    private static main_menu main;
    private static Scene scene;


    @Override
    public void start(Stage primaryStage){
        main = this;
        new MySQLConnection();

        //player = new Player(new Database().getUserID("Arose"),"Arose");
        //player2 = new Player(new Database().getUserID("Qasim"),"Qasim");

        stage =primaryStage;
        playerN=new Label();
        playerN2=new Label();
        newGame=new Label("New Game");
        signIn=new Label("Sign In");
        stats=new Label("Statics");
        exitL=new Label("Exit");

        newGame.setTextFill(Color.WHITESMOKE);
        signIn.setTextFill(Color.WHITESMOKE);
        stats.setTextFill(Color.WHITESMOKE);
        playerN.setTextFill(Color.WHITESMOKE);
        playerN2.setTextFill(Color.WHITESMOKE);
        exitL.setTextFill(Color.WHITESMOKE);
        newGame.setAlignment(Pos.CENTER);
        signIn.setAlignment(Pos.CENTER);
        stats.setAlignment(Pos.CENTER);
        exitL.setAlignment(Pos.CENTER);

        newGame.setLayoutY(200);
        newGame.setLayoutX(Screen.getSceneWidth() - 400);
        signIn.setLayoutY(300);
        signIn.setLayoutX(Screen.getSceneWidth() - 400);
        stats.setLayoutY(400);
        stats.setLayoutX(Screen.getSceneWidth() - 400);
        exitL.setLayoutY(500);
        exitL.setLayoutX(Screen.getSceneWidth() - 400);
        playerN.setLayoutY(150);
        playerN.setLayoutX(70);

        playerN2.setLayoutY(220);
        playerN2.setLayoutX(70);


        newGame.setOnMouseEntered(event -> newGame.setTextFill(Color.STEELBLUE));
        newGame.setOnMouseExited(event -> newGame.setTextFill(Color.WHITESMOKE));

        stats.setOnMouseEntered(event -> stats.setTextFill(Color.STEELBLUE));
        stats.setOnMouseExited(event -> stats.setTextFill(Color.WHITESMOKE));

        signIn.setOnMouseEntered(event -> signIn.setTextFill(Color.STEELBLUE));
        signIn.setOnMouseExited(event -> signIn.setTextFill(Color.WHITESMOKE));

        exitL.setOnMouseEntered(event -> exitL.setTextFill(Color.STEELBLUE));
        exitL.setOnMouseExited(event -> exitL.setTextFill(Color.WHITESMOKE));

        newGame.setOnMouseClicked(e -> NewGame());
        signIn.setOnMouseClicked(e -> SignIn());
        stats.setOnMouseClicked(e -> Stats());
        exitL.setOnMouseClicked(e -> closeGame());

        if(player != null) signIn.setText("Sign Out");

        playerN.setTextFill(Color.WHITE);
        playerN.setStyle("-fx-font-size: 30");
        playerN2.setTextFill(Color.WHITE);
        playerN2.setStyle("-fx-font-size: 30");

        Font font=new Font("Times New Roman",30);
        newGame.setFont(font);
        newGame.setStyle("-fx-font-size: 30");
        signIn.setStyle("-fx-font-size: 30 ");
        stats.setStyle("-fx-font-size: 30");
        exitL.setStyle("-fx-font-size: 30");


        group=new Group();
        canvas=new Canvas(Screen.getSceneWidth(),Screen.getFullScreenHeight());
        group.getChildren().addAll(canvas,newGame,signIn,stats,exitL,playerN,playerN2);
        scene =new Scene(group);

        GraphicsContext gc=canvas.getGraphicsContext2D();

        Image img=new Image("Images/mainback.jpg",Screen.getWidth(),Screen.getFullScreenHeight(),true,false);
        gc.drawImage(img,0,0);

        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        new StageDisplay(group);
        stage.getIcons().add(new Image("Images/icon.png"));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void NewGame()
    {
       if(player == null || player2 == null)
       {
           new SignInNotifiction(stage);
           return;
       }
       new GameSelect();
    }

    public void SignIn()
    {
        if(player2 != null)
        {
            new StatsSaveLoad(player2).saveStats();
            player2=null;
            playerN2.setText("");
            group.getChildren().removeAll(player2Icon);
            showMainMenu();
            return;
        }
        if(player != null)
        {
            new StatsSaveLoad(player).saveStats();
            player=null;
            playerN.setText("");
            group.getChildren().removeAll(player1Icon);
            showMainMenu();
            return;
        }
        new SignIn(stage);
    }

    private void Stats()
    {
        new Stats();
    }

    private void closeGame()
    {
        stage.close();
        MySQLConnection.getCurrentObj().endConnection();
        System.exit(0);
    }

    public void showMainMenu()
    {
        if(player != null)
        {
            signIn.setText("Sign Out");
        }
        else
        {
            signIn.setText("Sign In");
        }
        new StageDisplay(group);
    }

    public static main_menu getMain() {
        return main;
    }

    public void setUpPlayer(Player p)
    {
        if(player == null)
        {
            player=p;
            playerN.setText(p.getName());

            player1Icon = new Circle(40,40+120, 30);
            player1Icon.setFill(player.getIcon());
            group.getChildren().add(player1Icon);
        }
        else
        {
            player2 = p;
            playerN2.setText(p.getName());

            player2Icon = new Circle(40,115+120, 30);
            player2Icon.setFill(player2.getIcon());
            group.getChildren().add(player2Icon);
        }

    }

    public Stage getStage() {
        return stage;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Scene getScene() {return scene;}

    public Label getPlayerN() {
        return playerN;
    }
}
