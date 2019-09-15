package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.swing.Timer;
/** This Class extends Bonus and implements Runnable. 
 * This thread has the duty of slowing "time" and make enemies move slower, for a short period of time 
 */

public class GaryManager extends Bonus implements  Runnable {
    
    private static final int BONUS_DURATION = 10000;
    private final SpongebobGameController controller;
    /**this method is the constructor.
     * It instantiate the image and the game controller
     * @param base AnchorPane root
     * @param controller SpongebobGameController controller
     */
    public GaryManager(final AnchorPane base, final SpongebobGameController controller) {
        super(base, controller.getModel().getBonusDuration());
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
        Platform.runLater(() -> root.getChildren().remove(this.image));
        System.out.println("Ciao");
    }



    @Override
    public final  void run() {
        this.spawn(7, 3.5);
        this.move();
        this.image.setOnMouseClicked((event) -> action());
    }
}
