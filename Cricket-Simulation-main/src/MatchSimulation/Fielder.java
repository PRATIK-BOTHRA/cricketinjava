package MatchSimulation;

import javafx.scene.image.Image;

public class Fielder extends Moveable implements Game.CricketGame {
    double power=2.5;
    boolean moveable = true;

    Image player2=new Image("Images/fielder1.png",20,20,false,true);
    Image player1=new Image("Images/fielder2.png",20,20,false,true);

    public Fielder(int x, int y){
        this.setImage(player1);
        this.setPosition(x,y);
    }

    public void changeImage()
    {
        this.setImage(player2);
    }
    public void resetImage() {this.setImage(player1);}

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }



}
