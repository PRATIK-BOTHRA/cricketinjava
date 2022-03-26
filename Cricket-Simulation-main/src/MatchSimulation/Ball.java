package MatchSimulation;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.ThreadLocalRandom;


public class Ball extends Moveable implements Game.CricketGame {
    private boolean sameBall=false;
    private double x=0;
    private double y=-1;

    Image ball=new Image("Images/ball.png",15,15,false,true);


    public Ball(){
        super();
        super.setImage(ball);
    }
    public boolean move(Bat p, ArrayList<Fielder> b){

        if(super.intersects(p)){

            if(Cricket.getShotType() == 8)
            {
                setX(0);
                setY(0);
                setPosition(674,277);
                setVelocity(0,0);
                sameBall=true;
                return false;
            }
            if(Cricket.getShotType() == 0 && !sameBall)
            {
                setX(ThreadLocalRandom.current().nextDouble(-3,3));
                setY(ThreadLocalRandom.current().nextDouble(-3,3));
            }
            else if(!sameBall){
                setX(ThreadLocalRandom.current().nextDouble(-1,1));
                setY(ThreadLocalRandom.current().nextDouble(-1,1));
            }
            sameBall=true;
        }
        try {
            if(Cricket.getShotType() != 0)
            for(Fielder c:b){
                if(this.intersects(c)){

                    return false;
                }
            }
        }catch (ConcurrentModificationException e){
        }

        super.setVelocity(x,y);
        super.update(3);

        return true;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setSameBall(boolean sameBall) {
        this.sameBall = sameBall;
    }

}