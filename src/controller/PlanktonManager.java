package controller;

import java.util.concurrent.TimeUnit;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class PlanktonManager extends Thread {
  @FXML
private AnchorPane root;
  public ImageView plankton;
  private int interchanger = 0;
  private TranslateTransition transition = new TranslateTransition();
  private int duration;
  private static final int ANIMATION_TIME = 250;
  private static final int LIMIT = 60;
  private final Image[] interImages;
  private SpongebobGameController controller;
  private boolean canRun = true;
    
  /**this is a constructor.
     * 
     * @param base AnchorPane root
     * @param controller SpongebobGameController controller
     * @param images the 2 images of plankton walking
     */
  public PlanktonManager(final AnchorPane base, final SpongebobGameController controller, final Image[] images) {
    this.root = base;
    this.duration = controller.getModel().getPlanktonTime();
    this.interImages = images;
    this.plankton = new ImageView(interImages[0]);   
    this.controller = controller;
  }

  @Override
    public void run()  {
    randomSpawn();
    plankton.setFitHeight(this.root.getHeight() / 3);
    plankton.setFitWidth(this.root.getWidth() / 3);
    setTransition();
    while (this.canRun) {
      try {
        TimeUnit.MILLISECONDS.sleep(ANIMATION_TIME); // mantengo  questo per il momento
        if (interchanger == 0) {
          plankton.setImage(interImages[0]);
          interchanger = 1;
        } else {
          plankton.setImage(interImages[1]);
          interchanger = 0;
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**this method disables and stop thread.
   */
  public void disable() {
    if (this.canRun) {
      this.canRun = false; 
    }
  }

  /**
    * this method sets a random position to spawn the random enemy.
    */
  public void randomSpawn() {
    double lowerBound = (root.getWidth() * 10) / 100;
    double upperBound = (root.getWidth() * 70) / 100;
    int location = (int) ((Math.random() * (upperBound - lowerBound)) + lowerBound);
    this.plankton.setLayoutX(location);
    this.plankton.setLayoutY(0);
    this.plankton.setVisible(true);
  }
    
  /**this method set the transition for the enemy.
   * 
   */
  public void setTransition() {
    transition.setNode(this.plankton);
    transition.setFromY(0);
    transition.setToY((root.getHeight() * LIMIT) / 100);
    transition.setDuration(Duration.millis(this.duration));
    transition.play();
    transition.setOnFinished((event) -> Platform.runLater(() -> {
      this.controller.getModel().freeze();
      this.disable();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("exiting...");
      this.controller.quit();
    }));
  }
    
  public void stopTransition() {
    this.transition.stop();
  } 
    
  public void stermination() {
    Platform.runLater(() ->  this.root.getChildren().removeAll(this.plankton));
  }
    
}
