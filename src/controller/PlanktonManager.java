package controller;


import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.util.Duration;

import java.util.concurrent.TimeUnit;


public class PlanktonManager extends Thread {
    @FXML	
    private AnchorPane root;
    public ImageView Plankton = new ImageView(new Image(ClassLoader.getSystemResource("images/plankton4.png").toString()));
    private int interchanger = 0;
    private TranslateTransition transition = new TranslateTransition();
    private int duration;
    private static final int ANIMATION_TIME = 250;
    private static final int LIMIT = 70;
    private final Image[] InterImages;
    private SpongebobGameController controller;
    
    //constructor used in order to obtain the main panel.
    public PlanktonManager(final AnchorPane base, final SpongebobGameController controller, final Image[] images) {
        this.root = base;
        this.duration = controller.getModel().getPlanktonTime();
        this.InterImages = images;
        this.Plankton = new ImageView(InterImages[0]);   
        this.controller = controller;
    }

    //main function, after waiting a couple of seconds it randomly spawn a plankton
    public void run() {
        RandomSpawn();
        Plankton.setFitHeight(220);
        Plankton.setFitWidth(120);
        SetTransition();
        while(true){
            try {
                TimeUnit.MILLISECONDS.sleep(ANIMATION_TIME); // mantengo  questo per il momento
                if(interchanger==0){
                    Plankton.setImage(InterImages[0]);
                    interchanger=1;
                }else{
                    Plankton.setImage(InterImages[1]);
                    interchanger=0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //a function to set a random location of spawning
    public void RandomSpawn() {
        double lowerBound = (root.getWidth()*10)/100;
        double upperBound = (root.getWidth()*70)/100;
        int location = (int) ((Math.random()*(upperBound-lowerBound))+lowerBound);
        this.Plankton.setLayoutX(location);
        this.Plankton.setLayoutY(0);
        this.Plankton.setVisible(true);
    }
    
    //a function created in order to move plankton on the screen
    public void SetTransition(){
        transition.setNode(this.Plankton);
        transition.setFromY(0);
        transition.setToY((root.getHeight()*LIMIT)/100);
        transition.setDuration(Duration.millis(this.duration));
        transition.play();
        transition.setOnFinished((event)->Platform.runLater(()-> {
            this.controller.getModel().freeze();
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
    
    public void Stermination(){
        Platform.runLater(()-> this.root.getChildren().removeAll(this.Plankton));
    }
    
}
