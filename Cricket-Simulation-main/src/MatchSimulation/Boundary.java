package MatchSimulation;

import Menu.Screen;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

public class Boundary extends Moveable implements Game.CricketGame {
    Circle boundary=new Circle();
    Point2D point2D;
    public Boundary(){
        boundary.setCenterX(Screen.getWidth()/2);
        boundary.setCenterY(Screen.getFullScreenHeight()/3 - 50);
        boundary.setRadius(360);
        boundary.setVisible(false);

        //boundary.setFill(new ImagePattern(new Image("Images/ground.png",720,720,false,false)));
    }

    public boolean BoundaryScored(Ball ball){
        point2D=new Point2D(ball.getPositionX(),ball.getPositionY());
        if(boundary.contains(point2D)){
            return false;
        }else {
            return true;
        }
    }

    public Circle drawBoundary(){
        return boundary;
    }

}
