package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.swing.Timer;

/**This Class extends Bonus and implements Runnable. 
 * This thread has the duty of multiply the points obtained, for a short period of time  
 */
public class KrabbyPattyManager extends Bonus implements Runnable {
    
  private static final int BONUS_DURATION = 5000;
  private SpongebobGameController controller;

  /**
     * the constructor method.
     * it instantiate the image and the controller
     * @param base AnchorPane root
     * @param controller  SPongebobGameController controller
     */
  public KrabbyPattyManager(final AnchorPane base, final SpongebobGameController controller) {
    super(base, controller.getModel().getBonusDuration());
    this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/KrabbyPatty.png").toString()));
    this.controller = controller;
  }

  @Override
  public final  void action() {
    this.controller.getModel().setScoreBonus();
    final Timer timer = new Timer(BONUS_DURATION, (event) -> this.controller.getModel().setScoreBonus());
    timer.setRepeats(false);
    timer.start();
    Platform.runLater(() -> root.getChildren().remove(this.image));
  }


  @Override
  public final void run() {
    spawn(11.0,5.5);
    move();
    this.image.setOnMouseClicked((event) -> action());
  }
}

