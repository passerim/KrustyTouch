package controller;

import controller.Main;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public abstract class Bonus {
    
    protected AnchorPane root;
    protected ImageView image;;

    protected double randomPosition(){
        double MaxBound=(Main.root.getWidth()*75)/100;
        double MinBound=(Main.root.getWidth()*15)/100;
        double location =(Math.random()*(MaxBound-MinBound))+MinBound;
        return location;
    }

    protected void  move(){
        TranslateTransition movement= new TranslateTransition();
        movement.setNode(this.image);
        movement.setDuration(Duration.millis(3000));
        movement.setFromY(0);
        movement.setToY((Main.root.getHeight()*55)/100);
        movement.setOnFinished((event)-> Main.root.getChildren().remove(this.image));
        movement.play();
    }

    protected abstract void spawn();

    protected abstract void action();

}
