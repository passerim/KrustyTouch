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

    private static final int SCREEN_POSITION = 75;
    private static final int ANIMATION_TIME = 10000;
    private static final int MOVE_ANIMATION_TIME = 250;
    private static final int WIDTH_RATIO = 2;
    private static final int HEIGHT_RATIO = 4;
    private static SpongebobManager istanza = null;
    private final ImageView spongebob = new ImageView();
    private final SpongebobGameController controller;
    private int walkingPosition = 0;
    private final TranslateTransition movements = new TranslateTransition();

    /**
     * The singleton method, used to allow only one instance of the class.
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
        this.settingImage();
        Platform.runLater(() -> this.controller.getRoot().getChildren().add(this.spongebob));
        this.movements.setNode(this.spongebob);
        this.firstMove();
        while (true) {
            try {
                Thread.sleep(MOVE_ANIMATION_TIME);
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

    private void settingImage() {
        this.spongebob.setImage(new Image(ClassLoader.getSystemResource("images/walking_spongebob_1.png").toString()));
        this.spongebob.setLayoutX(0);
        this.spongebob.setLayoutY((this.controller.getRoot().getHeight() * SCREEN_POSITION) / 100);
        this.spongebob.setFitHeight(this.controller.getRoot().getHeight() / HEIGHT_RATIO);
        this.spongebob.setFitWidth(this.controller.getRoot().getWidth() / WIDTH_RATIO);
        this.spongebob.setVisible(true);
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
        this.movements.setDuration(Duration.millis(ANIMATION_TIME));
        this.movements.setOnFinished((event) -> moveLeft());
        this.movements.play();
    }

    private void moveLeft() {
        this.spongebob.setRotate(180);
        this.spongebob.setRotationAxis(new Point3D(0, 180, 1));
        this.movements.setFromX(this.controller.getRoot().getWidth() - this.spongebob.getFitWidth());
        this.movements.setToX(0);
        this.movements.setDuration(Duration.millis(ANIMATION_TIME));
        this.movements.setOnFinished((event) -> moveRight());
        this.movements.play();
    }

}


