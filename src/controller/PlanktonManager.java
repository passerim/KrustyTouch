package controller;

import java.util.concurrent.TimeUnit;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 */
public class PlanktonManager extends Thread {

    public ImageView plankton;
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
        randomSpawn();
        plankton.setFitHeight(this.controller.getRoot().getHeight() / 3);
        plankton.setFitWidth(this.controller.getRoot().getWidth() / 3);
        setTransition();
        while (this.canRun) {
            try {
                TimeUnit.MILLISECONDS.sleep(ANIMATION_TIME); // mantengo  questo per il momento
                if (interchanger == 0) {
                    plankton.setImage(interImages[0]);
                    interchanger = 1;
                } else {
                    plankton.setImage(interImages[1]);
                    interchanger = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**this method disables and stop thread.
     */
    public void disable() {
        if (this.canRun) {
            this.canRun = false; 
        }
    }

    /**
     * this method sets a random position to spawn the random enemy.
     */
    public void randomSpawn() {
        final double lowerBound = (this.controller.getRoot().getWidth() * 10) / 100;
        final double upperBound = (this.controller.getRoot().getWidth() * 70) / 100;
        final int location = (int) ((Math.random() * (upperBound - lowerBound)) + lowerBound);
        this.plankton.setLayoutX(location);
        this.plankton.setLayoutY(0);
        this.plankton.setVisible(true);
    }

    /**this method set the transition for the enemy.
     * 
     */
    public void setTransition() {
        transition.setNode(this.plankton);
        transition.setFromY(0);
        transition.setToY((this.controller.getRoot().getHeight() * LIMIT) / 100);
        transition.setDuration(Duration.millis(this.duration));
        transition.play();
        transition.setOnFinished((event) -> Platform.runLater(() -> {
            this.controller.getModel().freeze();
            this.disable();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("exiting...");
            this.controller.quit();
        }));
    }

    public void stopTransition() {
        this.transition.stop();
    } 

}
