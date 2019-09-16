package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This thread has the duty of destroying every opponent on the screen. 
 */
public class MrKrabManager extends Bonus implements Runnable {

    private static final double HEIGHT = 10.0;
    private static final double WIDTH = 5.0;
    private final SpongebobGameController controller;

    /**
     * this is the constructor method.
     * instantiate the image, and the controller
     * @param controller gameController interface, that manages all the functioning 
     */
    public MrKrabManager(final SpongebobGameController controller) {
        super(controller);
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/mrkrab_finale.png").toString()));
        this.controller = controller;
    }

    @Override
    public final void action() {
        this.controller.getModel().getMap().entrySet().stream().flatMap(e -> e.getValue().stream()).forEach(this::enemyDefeated);
        this.controller.getModel().getMap().entrySet().stream().map(e -> e.getValue()).forEach(l -> l.clear());
        Platform.runLater(() -> this.controller.removeNode(this.image));
    }

    private void enemyDefeated(final Plankton elem) {
        this.controller.removeNode(elem.getPlankton());
        this.controller.updateScore();
        elem.disable();
        elem.stopTransition();
    }

    @Override
    public final void run() {
        spawn(HEIGHT, WIDTH);
        move();
        this.image.setOnMouseClicked((event) -> action());
    }

}
