package Player;

import MISC.*;
import Menu.Screen;
import Menu.main_menu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class SignUp {
    private Label nameL,emailL,passL;
    private TextField name;
    private TextField email;
    private PasswordField pass;
    private Button create,back;
    private GridPane layout;
    private Stage stage;

    private Database d;

    public SignUp(Stage primaryStage){
        d = new Database();

        name=new TextField();
        email=new TextField();
        pass=new PasswordField();

        nameL=new Label("Name:");
        emailL=new Label("Email:");
        passL=new Label("Password");

        nameL.setTextFill(Color.WHITE);
        emailL.setTextFill(Color.WHITE);
        passL.setTextFill(Color.WHITE);

        create=new Button("Create");
        back=new Button("Back");

        layout=new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(10);
        layout.setHgap(10);

        int i=0;
        for(; i<Screen.getSceneWidth(); i+=75) layout.getColumnConstraints().add(new ColumnConstraints(75));
        int k=0;
        for(; k<Screen.getFullScreenHeight(); k+=50) layout.getRowConstraints().add(new RowConstraints(50));
        i/=75;
        k/=50;
        layout.setConstraints(nameL,i/2 - 2,k/2 - 10);
        layout.setConstraints(name,i/2 - 1,k/2 - 10);
        layout.setConstraints(emailL,i/2 - 2,k/2 - 9);
        layout.setConstraints(email,i/2 - 1,k/2 - 9);
        layout.setConstraints(passL,i/2 - 2,k/2 - 8);
        layout.setConstraints(pass,i/2 - 1,k/2 - 8);
        layout.setConstraints(create,i/2 - 2,k/2 - 7);
        layout.setConstraints(back,i/2 - 1,k/2 - 7);

        new ButtonsStyling(back,14);
        new ButtonsStyling(create,14);

        layout.getChildren().addAll(nameL,name,emailL,email,passL,pass,create,back);

        back.setOnAction(event -> {
            main_menu.getMain().SignIn();
        });

        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(name.getText().length() < 3 || name.getText().length() > 20)
                {
                    new LoginRegisterErrors(stage,"Invalid Username! Please enter valid username.\nMinimum 3 Characters.\nMaximum 20 Characters.");
                    return;
                }
                if(pass.getText().length() < 3 || pass.getText().length() > 50)
                {
                    new LoginRegisterErrors(stage,"Invalid Password! Please enter valid password.\nMinimum 3 Characters.\nMaximum 50 Characters.");
                    return;
                }
                if(!EmailChecker.validate(email.getText()))
                {
                    new LoginRegisterErrors(stage,"Invalid email! Please enter valid email.");
                    return;
                }

                if(d.signUp(name.getText().toLowerCase(),email.getText().toLowerCase(),pass.getText()))
                {
                    new WelcomeAboard(stage,name.getText(),true);
                }
                else {
                    if(d.checkUser(name.getText()))
                    {
                        new LoginRegisterErrors(stage,"Username already registered!");
                        return;
                    }
                    new LoginRegisterErrors(stage,"Email already registered!");
                    return;
                }
            }
        });

        BackgroundImage b = new BackgroundImage(new Image("Images/loginSignUpBack.jpg", Screen.getWidth(), Screen.getFullScreenHeight(),true,false), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        layout.setBackground(new Background(b));

        stage = primaryStage;
        new StageDisplay(layout);
    }
}
