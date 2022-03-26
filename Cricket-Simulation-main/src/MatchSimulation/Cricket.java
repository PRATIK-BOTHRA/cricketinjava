package MatchSimulation;

import MISC.ButtonsStyling;
import MISC.CustomDialog;
import MISC.PlayerInputDialog;
import MISC.StageDisplay;
import Menu.GameSelect;
import Menu.Screen;
import Menu.main_menu;
import Player.Player;
import com.mysql.cj.util.StringUtils;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Cricket implements Game.CricketGame {

    private static Cricket cricket = null;
    private static Group root;
    private static Pane pane;

    private Text player1Score;
    private Text player2Score;
    private Label playerN,playerN2;
    private static Player p2 = main_menu.getMain().getPlayer2();
    private static Player p1 = main_menu.getMain().getPlayer();

    private Button nextball,back,auto;

    private int settingA=0,settingB=650;
    private Canvas canvas;
    private ArrayList<Fielder> fielders;
    private ArrayList<Fielder> batsman;
    private Boundary boundary;

    private Bat bat;
    private Ball ball;

    private static boolean matchOver;
    private int score1,score2,wickets1,wickets2,balls1,balls2;
    private static int shotType;
    private int batting;

    private int totalOvers=1;
    private Random random = new Random();
    private AnimationTimer animationTimer;

    private Fielder selectedToMove;
    private Image groundPic;
    private boolean fieldMoveAble;

    private boolean cheatCodeFly;
    private boolean cheatCodeSix;
    private boolean cheatCodeOut;

    private Queue<Character> cheating= new LinkedList<>();


    private boolean autoMode;

    public static void start()
    {
        if(cricket == null || matchOver || p1 != main_menu.getMain().getPlayer() || p2 != main_menu.getMain().getPlayer2())
            cricket = new Cricket();
        else
            new StageDisplay(pane);
    }

    public Cricket()
    {
        cheatCodeSix=cheatCodeOut=cheatCodeFly = false;
        autoMode=false;

        p2 = main_menu.getMain().getPlayer2();
        p1 = main_menu.getMain().getPlayer();
        while(true)
        {
            PlayerInputDialog dialog = new PlayerInputDialog(main_menu.getMain().getStage(),"Name","Enter total overs\nMaximum Overs: 50\nMinimum Overs: 1","NEXT");
            if(StringUtils.isStrictlyNumeric(dialog.getText()))
            {
                totalOvers= Integer.parseInt(dialog.getText());
                if(totalOvers > 50 || totalOvers <1) continue;
                break;
            }
        }
        resetData();
        root=new Group();
        pane = new Pane();

        canvas = new Canvas(Screen.getSceneWidth(),Screen.getFullScreenHeight());

        canvas.setOnMouseClicked(this::handle);

        fielders = new ArrayList<>();
        batsman = new ArrayList<>();


        boundary = new Boundary();

        bat = new Bat();
        ball = new Ball();


        player1Score = new Text();
        player1Score.setLayoutY(settingA+100);
        player1Score.setLayoutX(settingA+10);
        player1Score.setStyle("-fx-font-size: 30px;\n"
                + "-fx-fill: purple;\n"
                + "-fx-stroke: black;\n"
                + "-fx-stroke-width: 0.1px;");

        player2Score = new Text();
        player2Score.setLayoutY(settingA+100);
        player2Score.setLayoutX(settingA+(Screen.getSceneWidth()/2)+boundary.boundary.getRadius()+10);
        player2Score.setStyle("-fx-font-size: 30px;\n"
                                    + "-fx-fill: yellow;\n"
                                    + "-fx-stroke: black;\n"
                                    + "-fx-stroke-width: 0.1px;");

        playerN= new Label();
        playerN.setLayoutY(30);
        playerN.setLayoutX(70);
        playerN.setTextFill(Color.BLACK);
        playerN.setStyle("-fx-font-size: 30;");
        playerN.setFont(Font.font(25));
        playerN.setText(p1.getName());
        Circle pc = new Circle(40,40, 30);
        pc.setFill(p1.getIcon());

        playerN2= new Label();
        playerN2.setStyle("-fx-font-size: 30");
        playerN2.setFont(Font.font(25));
        playerN2.setLayoutY(30);
        playerN2.setLayoutX(70 + settingA+(Screen.getSceneWidth()/2)+boundary.boundary.getRadius()+10);
        playerN2.setTextFill(Color.BLACK);
        playerN2.setText(p2.getName());
        Circle pc2 = new Circle(settingA+(Screen.getSceneWidth()/2)+boundary.boundary.getRadius()+40,40, 30);
        pc2.setFill(p2.getIcon());
        pc2.setStroke(Color.BLACK);

        Circle boundary= this.boundary.drawBoundary();

        nextball = new Button("Next Ball");
        back = new Button("Game Select");
        auto = new Button("Auto Mode");

        auto.setOnAction(event -> {
            autoMode=!autoMode;
            checkAutoButton();
        });
        nextball.setOnAction(event -> {
            if(matchOver)
            {
                displayMatchEnd();
                new Cricket();
                return;
            }
            setupShot();
            animationTimer.start();
        });

        back.setOnAction(event -> {
            new GameSelect();
        });

        new ButtonsStyling(nextball,40);
        new ButtonsStyling(back,40);
        new ButtonsStyling(auto,40);

        //nextball.setTextFill(Color.BLACK);
        //back.setTextFill(Color.BLACK);

        auto.setLayoutX(20);
        auto.setLayoutY(Screen.getFullScreenHeight()/2 - 100);

        nextball.setLayoutX(20);
        nextball.setLayoutY(Screen.getFullScreenHeight()/2);

        back.setLayoutX(settingA+(Screen.getSceneWidth()/2)+boundary.getRadius()+40);
        back.setLayoutY(Screen.getFullScreenHeight()/2);

        //Image background=new Image("Images/matchback.jpg", Screen.getWidth(),Screen.getFullScreenHeight(),false,false);
        pane.setBackground(new Background(new BackgroundFill(Color.rgb(108,187,17), CornerRadii.EMPTY, Insets.EMPTY)));
        //ImageView i = new ImageView(background);

        root.getChildren().addAll(canvas,boundary,player1Score,player2Score,playerN,playerN2,pc,pc2);
        pane.getChildren().addAll(/*i,*/root,back,nextball,auto);

        bat.setPosition( Screen.getWidth()/2 - 20,Screen.getFullScreenHeight()/2 - 320);
        ball.setPosition(Screen.getWidth()/2 - 10,Screen.getFullScreenHeight()/2 - 200);

        ResetPlayer();

        GraphicsContext gc=canvas.getGraphicsContext2D();
        pane.setOnKeyPressed(event -> {
            try {
                cheating.add(event.getText().toLowerCase().charAt(0));

                //System.out.println("Added: " + event.getText().toLowerCase().charAt(0) + "\n\tQueue :" + cheating.toString() + "\n\tQueue size:" + cheating.size());
                if (cheating.size() == 3) {
                    String cheatCode = new String();
                    while(!cheating.isEmpty())
                    {
                        cheatCode += cheating.remove();
                    }


                    if (cheatCode.equals("fly")) {
                        cheatCodeFly = !cheatCodeFly;
                        if (cheatCodeFly)
                            new CustomDialog(main_menu.getMain().getStage(), "Cheat Code", "WOOHA! CHEATER!!!! HACKS ON!!!\nYOUR FIELDERS CAN FLY!", "HACKERMAN", "Images/cheat.png",true);
                    }
                    if (cheatCode.equals("out")) {
                        cheatCodeOut = !cheatCodeOut;
                        if (cheatCodeOut) {
                            cheatCodeSix = false;
                            new CustomDialog(main_menu.getMain().getStage(), "Cheat Code", "WOOHA! CHEATER!!!! HACKS ON!!!\nYOUR PLAYERS WILL TAKE WICKET ON EACH BALL!", "HACKERMAN", "Images/cheat.png",true);
                        }
                    }
                    if (cheatCode.equals("six")) {
                        cheatCodeSix = !cheatCodeSix;
                        if (cheatCodeSix) {
                            cheatCodeOut = false;
                            new CustomDialog(main_menu.getMain().getStage(), "Cheat Code", "WOOHA! CHEATER!!!! HACKS ON!!!\nYOUR PLAYERS WILL HIT SIX ON EACH BALL!", "HACKERMAN", "Images/cheat.png",true);
                        }
                    }
                }
            }
            catch (Exception e)
            {

            }
            /*switch (event.getCode())
            {
                case F:
                    cheatingFly =1;
                    cheatingSix=cheatingOut=0;
                    break;

            }

            else if(event.getCode().equals(KeyCode.L))
            {
                cheatingSix=cheatingOut=0;
                if(cheatingFly == 1)
                    cheatingFly =2;
                else
                    cheatingFly =0;
            }
            else if(event.getCode().equals(KeyCode.Y))
            {
                cheatingSix=cheatingOut=0;
                if(cheatingFly == 2)
                {
                    cheatingFly =3;
                    cheatCodeFly =!cheatCodeFly;
                    if(cheatCodeFly) new CustomDialog(main_menu.getMain().getStage(),"Cheat Code","WOOHA! CHEATER!!!! HACKS ON!!!\nYOUR FIELDERS CAN FLY!","HACKERMAN","Images/cheat.png");
                }
                else
                    cheatingFly =0;
            }*/

        });

        groundPic =new Image("Images/ground.png", 770,770,false,false);
        gc.drawImage(groundPic,Screen.getWidth()/2 - 390,Screen.getFullScreenHeight()/3 - 440);
        bat.render(gc);
        for(Fielder c:fielders){
            c.render(gc);
        }

        for(Fielder c:batsman){
            c.changeImage();
            c.render(gc);
        }

        animationTimer= new AnimationTimer(){
            public void handle(long now){

                fieldMoveAble=false;
                gc.drawImage(groundPic,Screen.getWidth()/2 - 390,Screen.getFullScreenHeight()/3 - 440);
                bat.render(gc);
                for(Fielder c:fielders){
                    c.render(gc);
                }
                for(Fielder c:batsman){
                    c.render(gc);
                }

                boolean moving= ball.move(bat, fielders);

                if(Cricket.this.boundary.BoundaryScored(ball)){
                    try {
                        BallOver();
                        stop();
                        if(autoMode && !matchOver)
                        {
                            setupShot();
                            start();
                        }
                        scoreBoundry();
                    }catch (Exception e){}

                }
                if(!moving){
                    try {
                        BallOver();
                        stop();
                        if(autoMode && !matchOver)
                        {
                            setupShot();
                            start();
                        }
                        stoppedByFielder();

                    }catch (Exception e){}
                }

                ball.render(gc);
                canvas.setOnKeyPressed(event -> {
                    switch (event.getCode()){
                        case LEFT:
                            bat.move(-3);break;
                        case RIGHT:
                            bat.move(3);break;
                        case DOWN:
                            if(bat.getDownFactor()<5){
                                bat.setPositionY(bat.getPositionY()+5);
                                bat.setDownFactor(bat.getDownFactor()+1);}
                            break;
                    }
                });
                canvas.setOnKeyReleased(event -> {
                    if(event.getCode()== KeyCode.DOWN){
                        for(int a = 0; a< bat.getDownFactor(); a++){
                            bat.setPositionY(bat.getPositionY()-(5));
                            if(ball.intersects(bat)){
                                ball.setY(ball.getY()*(-bat.getDownFactor()));
                                ball.setPositionY(settingB-100);
                            }
                        }
                        bat.setDownFactor(0);
                    }
                });
                canvas.requestFocus();

            }
        };

        new StageDisplay(pane);
    }

    public void BallOver(){

        fieldMoveAble=true;
        ball.setSameBall(false);
        ball.setX(0);
        ball.setY(-1);
        ball.setPosition(Screen.getWidth()/2 - 10,Screen.getFullScreenHeight()/2 - 200);
    }

    public void ResetPlayer(){

        batsman.add(new Fielder(688,314));
        batsman.add(new Fielder(690,425));
        Random r = new Random();
        Fielder wicketKeeper = new Fielder(674,273);
        wicketKeeper.moveable=false;
        fielders.add(wicketKeeper);
        for(int i=1; i<10; i++)
        {
            int x = r.nextInt((int) ((Screen.getWidth()/2) - (boundary.boundary.getRadius()/2) + boundary.boundary.getRadius()));
            int y = r.nextInt((int) ((Screen.getFullScreenHeight()/3) - (boundary.boundary.getRadius()/2) + boundary.boundary.getRadius()));
            if(fieldPlaceAble(x,y))
            {
                fielders.add(new Fielder(x,y));
                continue;
            }
            i--;
            continue;
        }
    }

    private void setupShot()
    {
        shotType = random.nextInt(11);
        shotType= (shotType>9)?0:shotType;
        if(cheatCodeOut) shotType=8;
        if(cheatCodeSix) shotType=0;
        if(batting == 0)
        {
            p2.addBalls(this);
            balls1++;
        }
        else
        {
            p1.addBalls(this);
            balls2++;
        }
    }

    private void stoppedByFielder()
    {
        switch(shotType)
        {
            case 0:
            case 1:
                addScore();
                break;
            case 2:
            case 3:
            case 4:
            case 8:
            case 9:
                fallOfWicket();
                break;
            case 5:
            case 6:
            case 7:
                addScore();
                break;
        }
    }

    private void addScore()
    {
        if(batting == 0)
        {
            p1.addScore(this,1);
            score1++;
            player1Score.setText(setupScoreSheet(score1,wickets1,"Took a single",balls1));
        }
        else
        {
            p2.addScore(this,1);
            score2++;
            player2Score.setText(setupScoreSheet(score2,wickets2,"Took a single",balls2));
        }
        checkForInningsEnd();
    }

    private void fallOfWicket()
    {
        if(batting == 0)
        {
            p2.addWickets(this);
            wickets1++;
            player1Score.setText(setupScoreSheet(score1,wickets1,(shotType == 8)?"BOWLED OUT!!!":"CAUGHT OUT!!!",balls1));
        }
        else
        {
            p1.addWickets(this);
            wickets2++;
            player2Score.setText(setupScoreSheet(score2,wickets2,(shotType == 8)?"BOWLED OUT!!!":"CAUGHT OUT!!!",balls2));
        }
        checkForInningsEnd();
    }
    private void scoreBoundry()
    {
        boolean six = false;
        if(shotType > 6 || shotType == 0) six=true;
        if(batting == 0)
        {
            p1.addScore(this,(six)?6:4);
            if(six)
                p1.addSixes(this);
            else
                p1.addFours(this);
            score1 += (six)?6:4;

            player1Score.setText(setupScoreSheet(score1,wickets1,(six)?"SIX!":"FOUR!",balls1));
        }
        else
        {
            p2.addScore(this,(six)?6:4);
            if(six)
                p2.addSixes(this);
            else
                p2.addFours(this);
            score2 += (six)?6:4;
            player2Score.setText(setupScoreSheet(score2,wickets2,(six)?"SIX!":"FOUR!",balls2));
        }
        checkForInningsEnd();
    }

    private void checkForInningsEnd()
    {
        if(batting == 0)
        {
            if(balls1 == 6*totalOvers || wickets1 == 10)
            {
                player1Score.setText(setupScoreSheet(score1,wickets1,"Innings Over!",balls1));
                switchInnings();
            }
        }
        else
            if(balls2 == 6*totalOvers || wickets2 == 10 || score2 > score1)
            {
                player2Score.setText(setupScoreSheet(score2,wickets2,"Innings Over!",balls2));
                matchOver();
            }
    }
    private void switchInnings()
    {
        autoMode=false;
        checkAutoButton();
        animationTimer.stop();
        for(Fielder c:fielders)
            c.changeImage();
        for(Fielder c:batsman)
            c.resetImage();
        batting = 1;
    }

    private void matchOver()
    {
        animationTimer.stop();
        p1.inningsOver(this,score1);
        p2.inningsOver(this,score2);
        nextball.setText("New Match");
        matchOver=true;
        Player winner=null;
        if(score1 > score2)
        {
            winner=p1;
            p2.addLose(this);
        }
        else if(score2 > score1)
        {
            winner=p2;
            p1.addLose(this);
        }
        else
        {
            p1.addDraws(this);
            p2.addDraws(this);
            return;
        }
        winner.addWin(this);
    }

    private void displayMatchEnd()
    {
        String s;
        Player winner=null;
        if(score1 > score2)
            winner=p1;
        else if(score2 > score1)
            winner=p2;

        else
        {
            new CustomDialog(main_menu.getMain().getStage(),"Match Over","Guess What, You both Lost!","SAD","Images/joker.png",true);
            return;
        }
        s=winner.getName()+" WON THE GAME";
        new CustomDialog(main_menu.getMain().getStage(), "Match Over",s,"OK",winner,"Images/trophy.png",true);
    }

    private void resetData()
    {
        score1=score2=wickets1=wickets2=shotType=batting=0;
        balls1=balls2=0;
        matchOver=false;
        fieldMoveAble=true;
        p1.addGame(this);
        p2.addGame(this);
        cheatCodeOut=cheatCodeFly=cheatCodeSix=false;
        cheating = new LinkedList<>();
    }

    private String setupScoreSheet(int score, int wickets, String update, int balls)
    {
        String s = "Score: ";
        s+= score;
        s+="/" + wickets;
        s+="\nOvers: " + balls/6 + "." + balls % 6;
        if(!update.isEmpty()) s+="\n\nUpdate: " + update;
        return s;
    }

    private void handle(MouseEvent event) {
        //System.out.println("X = " +event.getX()+"\nY = " + event.getY());
        if(!fieldMoveAble && !cheatCodeFly) return;
        if(selectedToMove == null)
        for (Fielder c : fielders) {
            if (c.moveable) {
                if (event.getX() < c.getPositionX() + 40 && event.getX() > c.getPositionX() - 15 && event.getY() < c.getPositionY() + 40 && event.getY() > c.getPositionY() - 15) {
                    selectedToMove = c;
                    if(batting == 0)
                        player2Score.setText(setupScoreSheet(score2,wickets2,"Moving Fielder",balls2));
                    else
                        player1Score.setText(setupScoreSheet(score1,wickets1,"Moving Fielder",balls1));
                    return;
                }
            }
        }

        if(selectedToMove != null)
        {
            if(fieldPlaceAble((int) event.getX(),(int) event.getY()))
            {
                if(batting == 0)
                    player2Score.setText(setupScoreSheet(score2,wickets2,"Moved Fielder",balls2));
                else
                    player1Score.setText(setupScoreSheet(score1,wickets1,"Moved Fielder",balls1));
                selectedToMove.setPosition(event.getX(),event.getY());
                selectedToMove=null;
                updateCanvas();
            }
        }
    }

    private boolean fieldPlaceAble(int x, int y)
    {
        if(boundary.boundary.contains(x,y) && !(x > 646 && x < 726 && y > 300 && y < 450)) return true;
        return false;
    }

    private void updateCanvas()
    {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.drawImage(groundPic,Screen.getWidth()/2 - 390,Screen.getFullScreenHeight()/3 - 440);
        bat.render(g);
        ball.render(g);
        for(Fielder c:fielders){
            c.render(g);
        }
        for(Fielder c:batsman){
            c.render(g);
        }
    }

    private void checkAutoButton()
    {
        if(autoMode)
        {
            auto.setText("Stop Auto");
        }
        else auto.setText("Auto Mode");
    }

    public static int getShotType() {
        return shotType;
    }

}
