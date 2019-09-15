package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This thread has the duty of destroying every opponent on the screen 
 */
public class MrKrabManager extends Bonus implements Runnable {

    private final SpongebobGameController controller;

    /**
     * this is the constructor method.
     * instantiate the image, and the controller
     * @param base AnchorPane root 
     * @param controller gameController interface, that manages all the functioning 
     */
    public MrKrabManager(final SpongebobGameController controller) {
        super(controller);
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/mrkrab_finale.png").toString()));
        this.controller = controller;
    }

    @Override
    public final void action() {
        this.controller.getModel().getMap().entrySet().stream().flatMap(e -> e.getValue().stream()).forEach(elem -> {
            this.controller.removeNode(elem.plankton);
            this.controller.updateScore();
            elem.disable();
            elem.stopTransition();
        });
        this.controller.getModel().getMap().entrySet().stream().map(e -> e.getValue()).forEach(l -> l.clear());
        System.out.println("Ciao");
        Platform.runLater(() -> this.controller.removeNode(this.image));
    }

    @Override
    public final void run() {
        spawn(10.0, 5.0);
        move();
        this.image.setOnMouseClicked((event) -> action());
    }

}
