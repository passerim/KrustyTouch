package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**This Class extends Bonus and implements Runnable. 
 * This thread has the duty of destroying every opponent on the screen 
 */

public class MrKrabManager extends Bonus implements Runnable {

  private SpongebobGameController controller;
  
  /**
     * this is the constructor method.
     * instantiate the image, and the controller
     * @param base AnchorPane root 
     * @param controller gameController interface, that manages all the functioning 
     */
  public MrKrabManager(final AnchorPane base, final SpongebobGameController controller) {
    super(base, controller.getModel().getBonusDuration());
    this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/mrkrab_finale.png").toString()));
    this.controller = controller;
  }

  @Override
public final void action() {
        this.controller.getModel().getMap().entrySet().stream().flatMap(e -> e.getValue().stream()).forEach(elem -> {
          this.root.getChildren().remove(elem.plankton);
          this.controller.updateScore();
          elem.disable();
          elem.stopTransition();
        });
    this.controller.getModel().getMap().entrySet().stream().map(e -> e.getValue()).forEach(l -> l.clear());
    System.out.println("Ciao");
    Platform.runLater(() -> root.getChildren().remove(this.image));
  }

  @Override
public final void run() {
    spawn(10.0, 5.0);
    move();
    this.image.setOnMouseClicked((event) -> action());
  }

}
