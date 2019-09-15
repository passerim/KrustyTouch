package controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
/**
 * This Class, called SpongebobManager, is in charged of managing the Spongebob image moving on the screen. 
 * It implements a singleton in order to keep, and allow only an instance of it.
 */
public final class SpongebobManager extends Thread {
    
    private static SpongebobManager istanza = null;
    private final ImageView Spongebob = new ImageView();
    private final AnchorPane root;
    private int walking_position = 0;
    private final TranslateTransition movements = new TranslateTransition();
    /**the singleton method, used to allow only one instance of the class.
     * 
     * @param base AnchorPane root
     * @return the instance of the class
     */
    public static synchronized SpongebobManager getSpongebobManager(final AnchorPane base) {
        if (istanza == null) {
            istanza = new SpongebobManager(base);
        }
        return istanza;
    }
    
    private SpongebobManager(final AnchorPane base) {
        this.root = base;
    }
    @Override
    public void run() {
        this.Spongebob.setImage(new Image(ClassLoader.getSystemResource("images/walking_spongebob_1.png").toString()));
        this.Spongebob.setLayoutX(0);
        this.Spongebob.setLayoutY((this.root.getHeight()*75)/100);
        this.Spongebob.setFitHeight(this.root.getHeight()/4);
        this.Spongebob.setFitWidth(this.root.getWidth()/2);
        this.Spongebob.setVisible(true);
        Platform.runLater(() -> this.root.getChildren().add(this.Spongebob));
        movements.setNode(this.Spongebob);
        firstMove();
        while (true) {
            try {
                sleep(250);
                if (this.walking_position == 0) {
                    this.Spongebob.setImage(new Image(ClassLoader.getSystemResource("images/walking_spongebob_2.png").toString()));
                    this.walking_position = 1;
                } else {
                    this.Spongebob.setImage(new Image(ClassLoader.getSystemResource("images/walking_spongebob_1.png").toString()));
                    this.walking_position = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void firstMove() {
        this.Spongebob.setRotate(0);
        this.Spongebob.setRotationAxis(new Point3D(0, 0, 1));
        this.movements.setFromX(0);
        this.movements.setToX(0);
        this.movements.setDuration(Duration.millis(1000));
        this.movements.setOnFinished((event) -> moveRight());
        this.movements.play();
    }
    
    private void moveRight() {
        this.Spongebob.setRotate(0);
        this.Spongebob.setRotationAxis(new Point3D(0, 0, 1));
        this.movements.setFromX(0);
        this.movements.setToX(this.root.getWidth() - this.Spongebob.getFitWidth());
        this.movements.setDuration(Duration.millis(10000));
        this.movements.setOnFinished((event) -> moveLeft());
        this.movements.play();
    }
    
    private void moveLeft() {
        this.Spongebob.setRotate(180);
        this.Spongebob.setRotationAxis(new Point3D(0, 180, 1));
        this.movements.setFromX(this.root.getWidth() - this.Spongebob.getFitWidth());
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
        return this.Spongebob;
    }

}


