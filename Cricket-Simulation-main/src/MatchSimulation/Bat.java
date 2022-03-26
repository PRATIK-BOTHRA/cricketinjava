package MatchSimulation;

import javafx.scene.image.Image;

public class Bat extends Moveable implements Game.CricketGame {
    private int downFactor=0;
    Image rect=new Image("Images/bat.png",50,10,true,false);

    public Bat(){
        super.setImage(rect);
    }
    public void move(int x){

        super.setVelocity(x,0);
        super.update(10);
        if(super.getPositionX()<-10 || super.getPositionX()>=520){
            super.setVelocity(-x,0);
            super.update(10);
        }
    }


    public int getDownFactor() {
        return downFactor;
    }

    public void setDownFactor(int downFactor) {
        this.downFactor = downFactor;
    }
}