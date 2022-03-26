package Player;

import Game.CricketGame;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.LinkedList;
import java.util.Random;

public class Player {

    private int ID;
    private String name;
    private ImagePattern icon;
    private int played,wins,lost,draws;
    private int lowest,highest,score,wickets,sixes,fours,balls;

    {
        played=wins=lost=draws=0;
        lowest=highest=score=wickets=sixes=fours=balls=0;
    }

    private static LinkedList<ImagePattern> icons = new LinkedList<>();

    static {
        for(int i=0; i<15; i++) icons.add(new ImagePattern(new Image("Images/p"+i+".png",100,100,true,false)));
    }

    public Player(String name) {
        this(-1,name);
    }

    public Player(int ID, String name) {
        this(ID,name,false);
    }

    public Player(int ID, String name, boolean justDB) {
        this.ID = ID;
        this.name = name;
        if(!justDB)
        {
            if("Arose".equals(name))
            {
                icon = icons.getLast();
            }
            else
            {
                Random r = new Random();
                int p = r.nextInt(icons.size()-1);
                icon = icons.get(p);
                icons.remove(p);
            }

        }
        if(ID != -1)
        {
            new StatsSaveLoad(this).loadStats();
        }

    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public ImagePattern getIcon() {
        return icon;
    }

    public void addGame(Object game)
    {
        if(game instanceof CricketGame)
        {
            played++;
        }
    }

    public void addWin(Object game)
    {
        if(game instanceof CricketGame)
        {
            wins++;
        }
    }

    public void addLose(Object game)
    {
        if(game instanceof CricketGame)
        {
            lost++;
        }
    }

    public void addDraws(Object game)
    {
        if(game instanceof CricketGame)
        {
            draws++;
        }
    }

    public void addScore(Object game,int score)
    {
        if(game instanceof CricketGame)
        {
            this.score+=score;
        }
    }

    public void inningsOver(Object game,int score)
    {
        if(game instanceof CricketGame)
        {
            if(this.lowest == 0 || score < this.lowest)
                this.lowest=score;
            if(this.highest == 0 || score > this.highest)
                this.highest=score;
        }
    }

    public void addWickets(Object game)
    {
        if(game instanceof CricketGame)
        {
            wickets++;
        }
    }

    public void addSixes(Object game)
    {
        if(game instanceof CricketGame)
        {
            sixes++;
        }
    }

    public void addFours(Object game)
    {
        if(game instanceof CricketGame)
        {
            fours++;
        }
    }

    public void addBalls(Object game)
    {
        if(game instanceof CricketGame)
        {
            balls++;
        }
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }


    public void setLowest(int lowest) {
        this.lowest = lowest;
    }

    public void setHighest(int highest) {
        this.highest = highest;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public void setSixes(int sixes) {
        this.sixes = sixes;
    }

    public void setFours(int fours) {
        this.fours = fours;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public int getPlayed() {
        return played;
    }

    public int getWins() {
        return wins;
    }

    public int getLost() {
        return lost;
    }

    public int getDraws() {
        return draws;
    }

    public int getLowest() {
        return lowest;
    }

    public int getHighest() {
        return highest;
    }

    public int getScore() {
        return score;
    }

    public int getWickets() {
        return wickets;
    }

    public int getSixes() {
        return sixes;
    }

    public int getFours() {
        return fours;
    }

    public int getBalls() {
        return balls;
    }
}
