package controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * This is an Abstract Class that works as "Model" to implemented by the other bonus. 
 * Since most of the methods were very similar, they all got implemented here, with the exception of action 
 * which varies depending on the specific bonus.
 */
public abstract class Bonus implements Runnable {

    private static final int RIGHTOFFSET = 80;
    private static final int LEFTOFFSET = 20;
    private static final int LIMIT = 100;
    protected ImageView image;
    private final int bonusDuration;
    private final TranslateTransition movement = new TranslateTransition();
    private final SpongebobGameController controller;


    /**
     * 
     * @param controller
     *          reference to controller
     */
    public Bonus(final SpongebobGameController controller) {
        this.bonusDuration = controller.getModel().getBonusDuration();
        this.controller = controller;
    }

    /**
     * Computes random position on x coordinate of scene.
     * @return
     *          random position on x coordinate of scene
     */
    protected final double randomPosition() {
        final double maxBound = (this.controller.getRoot().getWidth() * RIGHTOFFSET) / 100;
        final double minBound = (this.controller.getRoot().getWidth() * LEFTOFFSET) / 100;
        return (Math.random() * (maxBound - minBound)) + minBound;
    }

    /**
     * Sets up and starts bonus transition.
     */
    protected final void move() {
        this.movement.setNode(this.image);
        this.movement.setDuration(Duration.millis(this.bonusDuration));
        this.movement.setFromY(0);
        this.movement.setToY((this.controller.getRoot().getHeight() * LIMIT) / 100);
        this.movement.setOnFinished((event) -> this.controller.removeNode(this.image));
        this.movement.play();
    }

    /**
     * Stops bonus transition.
     */
    public final void stopTransition() {
        this.movement.stop();
    }

    /**
     * Method to manage bonus spawn with selected height and width proportions.
     * @param heightSetting
     *          height ratio relative to scene's height
     * @param widthSetting
     *          width ratio relative to scene's width
     */
    protected final void spawn(final double heightSetting, final double widthSetting) {
        this.image.setFitHeight(this.controller.getRoot().getHeight() / heightSetting);
        this.image.setFitWidth(this.controller.getRoot().getWidth() / widthSetting);
        this.image.setLayoutY(0);
        this.image.setLayoutX(this.randomPosition());
        this.image.setVisible(true);
        Platform.runLater(() -> this.controller.addNode(this.image));
    }

    /** 
     * this abstract method is implemented in the various bonus classes.
     * Basically, it active the bonus effect on  the click of the mouse. 
     */
    protected abstract void action();

}
