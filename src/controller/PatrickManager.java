package controller;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
/**
 * This Class extends Bonus and implements Runnable. 
 * This thread has the duty of changing the symbol on the balloon, and maintaining, for a short period of time  
 */
public class PatrickManager extends Bonus implements Runnable {
    
    private static final int BONUS_DURATION = 10000;
    private final SpongebobGameController controller;
    /**
     * the constructor method.
     * it instantiate the image and the controller
     * @param base AnchorPane root
     * @param controller SPongebobGameController controller
     */
    public PatrickManager(final AnchorPane base, final SpongebobGameController controller){
        super(base, controller.getModel().getBonusDuration());
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/patrickstella.png").toString()));
        this.controller = controller;
    }

    @Override
    public final void action() {
        if (SpawnerPlanktonManager.getPlanktonSpawner(this.root, this.controller).onBonus()) {
            final Timer timer = new Timer(BONUS_DURATION, (event) -> SpawnerPlanktonManager.getPlanktonSpawner(this.root, this.controller).offBonus());
            timer.setRepeats(false);
            timer.start();
        }
        Platform.runLater(() -> {
            this.root.getChildren().remove(this.image);
            this.stopTransition();
        });
    }

    @Override
    public final void run() {
        spawn(10.0, 6.0);
        move();
        this.image.setOnMouseClicked((event) -> action());
    }
}
