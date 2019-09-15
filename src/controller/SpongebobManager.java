package controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * This Class, called SpongebobManager,
 * is in charged of managing the Spongebob image moving on the screen. 
 * It implements a singleton in order to keep, and allow only an instance of it.
 */
public final class SpongebobManager extends Thread {
    
  private static SpongebobManager istanza = null;
  private final ImageView spongebob = new ImageView();
  private final SpongebobGameController controller;
  private int walkingPosition = 0;
  private final TranslateTransition movements = new TranslateTransition();
    
  /**the singleton method, used to allow only one instance of the class.
     * 
     * @param controller 
     *          game controller reference
     * @return 
     *          the instance of the class
     */
  public static synchronized SpongebobManager getSpongebobManager(final SpongebobGameController controller) {
    if (istanza == null) {
      istanza = new SpongebobManager(controller);
    }
    return istanza;
  }
    
    private SpongebobManager(final SpongebobGameController controller) {
        super();
        this.controller = controller;
    }
    @Override
    public void run() {
        this.spongebob.setImage(new Image(ClassLoader.getSystemResource("images/walking_spongebob_1.png").toString()));
        this.spongebob.setLayoutX(0);
        this.spongebob.setLayoutY((this.controller.getRoot().getHeight()*75)/100);
        this.spongebob.setFitHeight(this.controller.getRoot().getHeight()/4);
        this.spongebob.setFitWidth(this.controller.getRoot().getWidth()/2);
        this.spongebob.setVisible(true);
        Platform.runLater(() -> this.controller.getRoot().getChildren().add(this.spongebob));
        movements.setNode(this.spongebob);
        firstMove();
        while (true) {
            try {
                sleep(250);
                if (this.walkingPosition == 0) {
                    this.spongebob.setImage(new Image(ClassLoader.getSystemResource("images/walking_spongebob_2.png").toString()));
                    this.walkingPosition = 1;
                } else {
                    this.spongebob.setImage(new Image(ClassLoader.getSystemResource("images/walking_spongebob_1.png").toString()));
                    this.walkingPosition = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void firstMove() {
        this.spongebob.setRotate(0);
        this.spongebob.setRotationAxis(new Point3D(0, 0, 1));
        this.movements.setFromX(0);
        this.movements.setToX(0);
        this.movements.setDuration(Duration.millis(1000));
        this.movements.setOnFinished((event) -> moveRight());
        this.movements.play();
    }
    
    private void moveRight() {
        this.spongebob.setRotate(0);
        this.spongebob.setRotationAxis(new Point3D(0, 0, 1));
        this.movements.setFromX(0);
        this.movements.setToX(this.controller.getRoot().getWidth() - this.spongebob.getFitWidth());
        this.movements.setDuration(Duration.millis(10000));
        this.movements.setOnFinished((event) -> moveLeft());
        this.movements.play();
    }
    
    private void moveLeft() {
        this.spongebob.setRotate(180);
        this.spongebob.setRotationAxis(new Point3D(0, 180, 1));
        this.movements.setFromX(this.controller.getRoot().getWidth() - this.spongebob.getFitWidth());
        this.movements.setToX(0);
        this.movements.setDuration(Duration.millis(10000));
        this.movements.setOnFinished((event) -> moveRight());
        this.movements.play();
    }

    /** this method, is quite self-explanatory.
     * 
     * @return the ImageView of Spongebob
     */
    public ImageView getSpongebob() {
        return this.spongebob;
    }

}


