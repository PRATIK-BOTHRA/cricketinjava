package Menu;

import MISC.ButtonsStyling;
import MISC.StageDisplay;
import Player.Database;
import Player.Player;
import Player.StatsSaveLoad;
import Player.TopPlayers;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Stats {
    private Button bestplayer,topplayer,mystats,mystats2,back;
    private GridPane layout;
    private Stage stage;

    public Stats(){
        new StatsSaveLoad(main_menu.getMain().getPlayer()).saveStats();
        new StatsSaveLoad(main_menu.getMain().getPlayer2()).saveStats();
        bestplayer = new Button("Best Player: "+ TopPlayers.bestPlayer());
        topplayer = new Button("Total Stats");
        if(main_menu.getMain().getPlayer() != null) mystats = new Button(main_menu.getMain().getPlayer().getName()+ "'s Stats");
        else
        {
            mystats= new Button("Player 1's Stats");
            mystats.setDisable(true);
        }
        if(main_menu.getMain().getPlayer2() != null) mystats2 = new Button(main_menu.getMain().getPlayer2().getName() + "'s stats");
        else
        {
            mystats2= new Button("Player 2's Stats");
            mystats2.setDisable(true);
        }
        back = new Button("Back");

        layout=new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(30);
        layout.setHgap(10);

        int i=0;

        int k=0;
        for(; k< Screen.getFullScreenHeight(); k+=70) layout.getRowConstraints().add(new RowConstraints(70));
        k/=70;
        k+=3;
        layout.setConstraints(bestplayer, i,k/2 - 9);
        layout.setConstraints(topplayer, i,k/2 - 8);
        layout.setConstraints(mystats, i,k/2 - 7);
        layout.setConstraints(mystats2, i,k/2 - 6);
        layout.setConstraints(back,i,k/2 - 5);

        new ButtonsStyling(bestplayer,20);
        new ButtonsStyling(topplayer,20);
        new ButtonsStyling(mystats,20);
        new ButtonsStyling(mystats2,20);
        new ButtonsStyling(back,20);


        layout.getChildren().addAll(bestplayer,mystats,mystats2,back);

        bestplayer.setOnAction(event -> new PlayerStatsDisplay(new Player(new Database().getUserID(TopPlayers.bestPlayer()),TopPlayers.bestPlayer(),true)));

        mystats.setOnAction(event -> new PlayerStatsDisplay(main_menu.getMain().getPlayer()));

        mystats2.setOnAction(event -> new PlayerStatsDisplay(main_menu.getMain().getPlayer2()));

        back.setOnAction(event -> {
            main_menu.getMain().showMainMenu();
        });

        BackgroundImage b = new BackgroundImage(new Image("Images/statsBack.jpg", Screen.getWidth(), Screen.getFullScreenHeight(),true,false), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        layout.setBackground(new Background(b));

        stage = main_menu.getMain().getStage();
        new StageDisplay(layout);
    }
}
