package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.Timer;


/**This thread has the duty of slowing "time" and make enemies move slower.
 */
public class GaryManager extends Bonus implements  Runnable {
    
	private static final double HEIGHT =7.0 ;
	private static final double WIDTH =3.5;
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
    }


    @Override
    public final  void run() {
        this.spawn(HEIGHT, WIDTH);
        this.move();
        this.image.setOnMouseClicked((event) -> action());
    }
    

}
