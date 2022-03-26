package Menu;

import MISC.ButtonsStyling;
import MISC.StageDisplay;
import Player.Player;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PlayerStatsDisplay {
    private Button matches,won,lost,draws,lowest,highest,score,sixes,fours,balls,wickets,back;
    private GridPane layout;
    private Stage stage;

    public PlayerStatsDisplay(Player bestPlayer){
        matches = new Button("Matches: "+ bestPlayer.getPlayed());
        won = new Button("Won: "+ bestPlayer.getWins());
        lost = new Button("Lost: "+ bestPlayer.getLost());
        draws = new Button("Drew: "+ bestPlayer.getDraws());

        lowest = new Button("Lowest: "+ bestPlayer.getLowest());
        highest = new Button("Highest: "+ bestPlayer.getHighest());

        score = new Button("Score (T): "+ bestPlayer.getScore());
        sixes = new Button("Sixes: "+ bestPlayer.getSixes());
        fours = new Button("Fours: "+ bestPlayer.getFours());

        balls = new Button("Balls: "+ bestPlayer.getBalls());
        wickets = new Button("Wickets: "+ bestPlayer.getWickets());

        matches.setDisable(true);
        won.setDisable(true);
        lost.setDisable(true);
        draws.setDisable(true);
        lowest.setDisable(true);
        highest.setDisable(true);
        score.setDisable(true);
        sixes.setDisable(true);
        fours.setDisable(true);
        balls.setDisable(true);
        wickets.setDisable(true);

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
        layout.setConstraints(matches, i,k/2 - 9); layout.setConstraints(won, i+1,k/2 - 9); layout.setConstraints(lost, i+2,k/2 - 9); layout.setConstraints(draws, i+3,k/2 - 9);
        layout.setConstraints(lowest, i,k/2 - 8); layout.setConstraints(highest, i+1,k/2 - 8);
        layout.setConstraints(score, i,k/2 - 7); layout.setConstraints(sixes, i+1,k/2 - 7); layout.setConstraints(fours, i+2,k/2 - 7);
        layout.setConstraints(balls, i,k/2 - 6); layout.setConstraints(wickets, i+1,k/2 - 6);
        layout.setConstraints(back,i,k/2 - 5);

        int size = 20;
        new ButtonsStyling(matches,size);
        new ButtonsStyling(won,size);
        new ButtonsStyling(lost,size);
        new ButtonsStyling(draws,size);
        new ButtonsStyling(lowest,size);
        new ButtonsStyling(highest,size);
        new ButtonsStyling(score,size);
        new ButtonsStyling(sixes,size);
        new ButtonsStyling(fours,size);
        new ButtonsStyling(balls,size);
        new ButtonsStyling(wickets,size);

        new ButtonsStyling(back,20);


        layout.getChildren().addAll(matches,won,lost,draws,lowest,highest,score,sixes,fours,balls,wickets,back);

        back.setOnAction(event -> {
            new Stats();
        });

        BackgroundImage b = new BackgroundImage(new Image("Images/bestBack.jpg", Screen.getWidth(), Screen.getFullScreenHeight(),true,false), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        layout.setBackground(new Background(b));

        stage = main_menu.getMain().getStage();
        new StageDisplay(layout);
    }
}
