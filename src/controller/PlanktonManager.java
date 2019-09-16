package controller;

import java.util.concurrent.TimeUnit;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *  This class is in charged of managing the enemies.
 */
public class PlanktonManager implements Plankton {

    private static final int DIMENSION_RATIO = 3;
    private static final int RIGHTOFFSET = 70;
    private static final int LEFTOFFSET = 10;
    private final ImageView plankton;
    private int interchanger = 0;
    private final TranslateTransition transition = new TranslateTransition();
    private final int duration;
    private static final int ANIMATION_TIME = 250;
    private static final int LIMIT = 60;
    private final Image[] interImages;
    private final SpongebobGameController controller;
    private boolean canRun = true;

    /**
     * @param controller SpongebobGameController controller
     * @param images the 2 images of plankton walking
     */
    public PlanktonManager(final SpongebobGameController controller, final Image[] images) {
        super();
        this.duration = controller.getModel().getPlanktonTime();
        this.interImages = images;
        this.plankton = new ImageView(interImages[0]);   
        this.controller = controller;
    }

    @Override
    public void run()  {
        this.randomSpawn();
        this.plankton.setFitHeight(this.controller.getRoot().getHeight() / DIMENSION_RATIO);
        this.plankton.setFitWidth(this.controller.getRoot().getWidth() / DIMENSION_RATIO);
        this.setTransition();
        while (this.canRun) {
            try {
                TimeUnit.MILLISECONDS.sleep(ANIMATION_TIME); // mantengo  questo per il momento
                if (this.interchanger == 0) {
                    this.plankton.setImage(this.interImages[0]);
                    this.interchanger = 1;
                } else {
                    this.plankton.setImage(this.interImages[1]);
                    this.interchanger = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void disable() {
        if (this.canRun) {
            this.canRun = false; 
        }
    }

    /**
     * This method sets a random position to spawn the random enemy.
     */
    private void randomSpawn() {
        final double lowerBound = (this.controller.getRoot().getWidth() * LEFTOFFSET) / 100;
        final double upperBound = (this.controller.getRoot().getWidth() * RIGHTOFFSET) / 100;
        final int location = (int) ((Math.random() * (upperBound - lowerBound)) + lowerBound);
        this.plankton.setLayoutX(location);
        this.plankton.setLayoutY(0);
        this.plankton.setVisible(true);
    }

    /** 
     * This method set the transition for the enemy.
     */
    private void setTransition() {
        transition.setNode(this.plankton);
        transition.setFromY(0);
        transition.setToY((this.controller.getRoot().getHeight() * LIMIT) / 100);
        transition.setDuration(Duration.millis(this.duration));
        transition.play();
        transition.setOnFinished((event) -> Platform.runLater(() -> endingSequence()));
    }

    private void endingSequence() {
        this.controller.getModel().freeze();
        this.disable();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.controller.quit();
    }
    
    @Override
    public ImageView getPlankton() {
        return plankton;
    }

    @Override
    public void stopTransition() {
        this.transition.stop();
    } 

}
