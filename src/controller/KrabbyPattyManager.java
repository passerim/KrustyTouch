package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.Timer;

/**
 * This thread has the duty of multiply the points obtained, for a short period of time. 
 */
public class KrabbyPattyManager extends Bonus implements Runnable {
    
	private static final double HEIGHT=11.0;
	private static final double WIDTH=5.5;
    private static final int BONUS_DURATION = 5000;
    private final SpongebobGameController controller;

    /**
     * It instantiate the image and the controller.
     * @param controller  SPongebobGameController controller
     */
    public KrabbyPattyManager(final SpongebobGameController controller) {
        super(controller);
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/KrabbyPatty.png").toString()));
        this.controller = controller;
    }

    @Override
    public final  void action() {
        this.controller.getModel().setScoreBonus();
        final Timer timer = new Timer(BONUS_DURATION, (event) -> this.controller.getModel().setScoreBonus());
        timer.setRepeats(false);
        timer.start();
        Platform.runLater(() -> this.controller.removeNode(this.image));
    }


  @Override
  public final void run() {
    spawn(HEIGHT, WIDTH);
    move();
    this.image.setOnMouseClicked((event) -> action());
  }
}

