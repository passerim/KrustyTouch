package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javax.swing.Timer;

public class GaryManager extends Bonus implements  Runnable {
    
    private static final int BONUS_DURATION = 10000;
    private SpongebobGameController controller;
    
    public GaryManager(AnchorPane base, SpongebobGameController controller){
        super(base, controller.getModel().getBonusDuration());
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/gary_finale.png").toString()));
        this.controller = controller;
    }

    @Override
    public void spawn() {
        this.image.setFitHeight(this.root.getHeight()/7);
        this.image.setFitWidth(this.root.getWidth()/3.5);
        this.image.setLayoutY(0);
        this.image.setLayoutX(this.randomPosition());
        this.image.setVisible(true);
        Platform.runLater(()->root.getChildren().add(this.image));
    }

    @Override
    public void action() {
        final int oldTime = this.controller.getModel().getPlanktonTime();
        this.controller.getModel().delayBonus(oldTime);
        final Timer timer = new Timer(BONUS_DURATION,(event)->this.controller.getModel().delayBonus(oldTime));
        timer.setRepeats(false);
        timer.start();
        Platform.runLater(()->root.getChildren().remove(this.image));
        System.out.println("Ciao");
    }



    @Override
    public void run() {
        this.spawn();
        this.move();
        this.image.setOnMouseClicked((event)->action());
    }
}
