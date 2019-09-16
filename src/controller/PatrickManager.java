package controller;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This Class extends Bonus and implements Runnable. 
 * This thread has the duty of changing the symbol on the balloon,
 *  and maintaining, for a short period of time  
 */
public class PatrickManager extends Bonus implements Runnable {
    private static final double HEIGHT = 10.0;
    private static final double WIDTH = 6.0;  
    private static final int BONUS_DURATION = 10000;
    private final SpongebobGameController controller;

    /**
     * the constructor method.
     * it instantiate the image and the controller
     * @param controller SPongebobGameController controller
     */
    public PatrickManager(final SpongebobGameController controller) {
        super(controller);
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/patrickstella.png").toString()));
        this.controller = controller;
    }

    @Override
    public final void action() {
        if (SpawnerPlanktonManager.getPlanktonSpawner(this.controller).onBonus()) {
            final Timer timer = new Timer(BONUS_DURATION, (elem) -> stopBonus());
            timer.setRepeats(false);
            timer.start();
        }
        Platform.runLater(() -> {
            this.controller.removeNode(this.image);
            this.stopTransition();
        });
    }

    private boolean stopBonus() {
        return SpawnerPlanktonManager.getPlanktonSpawner(this.controller).offBonus();
    }


    @Override
    public final void run() {
        spawn(HEIGHT, WIDTH);
        move();
        this.image.setOnMouseClicked((event) -> action());
    }
}
