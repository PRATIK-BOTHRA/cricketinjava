package Player;

import MISC.ButtonsStyling;
import MISC.LoginRegisterErrors;
import MISC.StageDisplay;
import MISC.WelcomeAboard;
import Menu.Screen;
import Menu.main_menu;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SignIn {

    private TextField name;
    private PasswordField pw;
    private Stage stage;
    private Pane layoutPane;
    private Button loginButton,SignUPButton,backButton;

    private Database database;

    public SignIn(Stage primaryStage){
        database=new Database();

        name = new TextField();
        name.setPromptText("NAME");

        pw = new PasswordField();
        pw.setPromptText("PASSWORD");
        pw.setOnAction(event -> loginEvent());

        loginButton = new Button("Login");
        loginButton.setOnAction(event -> loginEvent());

        new ButtonsStyling(loginButton,14);


        SignUPButton = new Button("Sign Up");
        new ButtonsStyling(SignUPButton,14);
        SignUPButton.setOnAction(event -> new SignUp(stage));

        backButton = new Button("Main Menu");
        new ButtonsStyling(backButton,14);
        backButton.setOnAction(event -> main_menu.getMain().showMainMenu());

        layoutPane = new Pane();
        layoutPane.getChildren().addAll(loginButton, SignUPButton,name,pw,backButton);
        BackgroundImage b = new BackgroundImage(new Image("Images/loginSignUpBack.jpg", Screen.getWidth(),Screen.getFullScreenHeight(),true,false), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        layoutPane.setBackground(new Background(b));
        //scene = new Scene(layoutPane, Screen.getSceneWidth(), Screen.getFullScreenHeight());


        name.setLayoutX(Screen.getScreenCenter() - 50);
        name.setLayoutY(Screen.getFullScreenHeight() / 2 - 300);
        pw.setLayoutX(Screen.getScreenCenter() - 50);
        pw.setLayoutY(Screen.getFullScreenHeight() / 2 - 260);
        loginButton.setLayoutX(Screen.getScreenCenter() - 60);
        loginButton.setLayoutY(Screen.getFullScreenHeight() / 2 - 200);
        SignUPButton.setLayoutX(Screen.getScreenCenter() + 30);
        SignUPButton.setLayoutY(Screen.getFullScreenHeight() / 2 - 200);
        backButton.setLayoutX(Screen.getScreenCenter() - 20);
        backButton.setLayoutY(Screen.getFullScreenHeight() / 2 - 160);

        stage = primaryStage;
        new StageDisplay(layoutPane);
    }

    private void loginEvent()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);

        if(main_menu.getMain().getPlayer() != null)
        {
            if(name.getText().equals(main_menu.getMain().getPlayer().getName()))
            {
                new LoginRegisterErrors(main_menu.getMain().getStage(),"User already logged in!");
                return;
            }

        }

        if(database.checkUser(name.getText()))
        {
            if(database.getUser(name.getText(),new String(pw.getText())))
            {
                new WelcomeAboard(main_menu.getMain().getStage(),name.getText());
                Player p =new Player(database.getUserID(name.getText()),name.getText());
                main_menu.getMain().setUpPlayer(p);
                main_menu.getMain().showMainMenu();
                return;
            }
            else
            {
                new LoginRegisterErrors(main_menu.getMain().getStage(),"Incorrect Password!");
            }
        }
        else
        {
            new LoginRegisterErrors(main_menu.getMain().getStage(),"Incorrect Username!");
        }
    }

}
