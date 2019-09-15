package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.Timer;

/** 
 * This thread has the duty of slowing "time" and make enemies move slower, for a short period of time.
 */
public class GaryManager extends Bonus implements  Runnable {
    
    private static final int BONUS_DURATION = 10000;
    private final SpongebobGameController controller;
    
    /**
     * It instantiates the image and the game controller.
     * @param controller SpongebobGameController controller
     */
    public GaryManager(final SpongebobGameController controller) {
        super(controller);
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/gary_finale.png").toString()));
        this.controller = controller;
    }

    @Override
    public  final void action() {
        final int oldTime = this.controller.getModel().getPlanktonTime();
        if (this.controller.getModel().onDelayBonus()) {
            final Timer timer = new Timer(BONUS_DURATION, (event) -> this.controller.getModel().offDelayBonus(oldTime));
            timer.setRepeats(false);
            timer.start();
        }
        Platform.runLater(() -> this.controller.removeNode(this.image));
        System.out.println("Ciao");
    }

    @Override
    public final  void run() {
        this.spawn(7, 3.5);
        this.move();
        this.image.setOnMouseClicked((event) -> action());
    }
    
}
