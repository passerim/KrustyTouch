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
    private TranslateTransition movement= new TranslateTransition();
    
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

    protected void move(){
        this.movement.setNode(this.image);
        this.movement.setDuration(Duration.millis(this.bonusDuration));
        this.movement.setFromY(0);
        this.movement.setToY((this.root.getHeight()*LIMIT)/100);
        this.movement.setOnFinished((event)-> this.root.getChildren().remove(this.image));
        this.movement.play();
    }
    
    public void stopTransition() {
        this.movement.stop();
    }

    protected abstract void spawn();

    protected abstract void action();

}
