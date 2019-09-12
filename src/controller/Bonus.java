package controller;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public abstract class Bonus implements Runnable {
    
    private static final int RIGHTOFFSET = 100;
    private static final int LEFTOFFSET = 0;
    private static final int LIMIT = 100;
    protected AnchorPane root;
    protected ImageView image;
    private final int bonusDuration;
    
    public Bonus(final AnchorPane base, final int duration) {
        this.root = base;
        this.bonusDuration = duration;
    }

    protected double randomPosition(){
        double MaxBound=(this.root.getWidth()*RIGHTOFFSET)/100;
        double MinBound=(this.root.getWidth()*LEFTOFFSET)/100;
        double location =(Math.random()*(MaxBound-MinBound))+MinBound;
        return location;
    }

    protected void  move(){
        TranslateTransition movement= new TranslateTransition();
        movement.setNode(this.image);
        movement.setDuration(Duration.millis(this.bonusDuration));
        movement.setFromY(0);
        movement.setToY((this.root.getHeight()*LIMIT)/100);
        movement.setOnFinished((event)-> this.root.getChildren().remove(this.image));
        movement.play();
    }

    protected abstract void spawn();

    protected abstract void action();

}
