package controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * This is an Abstract Class that works as "Model" to implemented by the other bonus. 
 * Since most of the methods were very similar, they all got implemented here, with the exception of action 
 * which varies depending on the specific bonus.
 */
public abstract class Bonus implements Runnable {
    
    protected static final int RIGHTOFFSET = 80;
    protected static final int LEFTOFFSET = 20;
    protected static final int LIMIT = 100;
    protected AnchorPane root;
    protected ImageView image;
    private final int bonusDuration;
    private final TranslateTransition movement = new TranslateTransition();
    /**
     * the constructor method.
     * @param base AnchorPane base
     * @param duration the duration of the bonus effect
     */
    public Bonus(final AnchorPane base, final int duration) {
        this.root = base;
        this.bonusDuration = duration;
    }

    protected final double randomPosition() {
        final double MaxBound = (this.root.getWidth() * RIGHTOFFSET) / 100;
        final double MinBound = (this.root.getWidth() * LEFTOFFSET) / 100;
        final double location = (Math.random() * (MaxBound - MinBound)) + MinBound;
        return location;
    }

    protected void move() {
        this.movement.setNode(this.image);
        this.movement.setDuration(Duration.millis(this.bonusDuration));
        this.movement.setFromY(0);
        this.movement.setToY((this.root.getHeight() * LIMIT) / 100);
        this.movement.setOnFinished((event) -> this.root.getChildren().remove(this.image));
        this.movement.play();
    }
    
    public void stopTransition() {
        this.movement.stop();
    }

    protected void spawn(double HeightSetting, double WidthSetting) {
    	this.image.setFitHeight(this.root.getHeight()/HeightSetting);
        this.image.setFitWidth(this.root.getWidth()/WidthSetting);
        this.image.setLayoutY(0);
        this.image.setLayoutX(this.randomPosition());
        this.image.setVisible(true);
        Platform.runLater(()->root.getChildren().add(this.image));
    }
    /** this abstract method is implemented in the various bonus classes.
     * Basically, it active the bonus effect on  the click of the mouse. 
     * 
     */
    protected abstract void action();

}
