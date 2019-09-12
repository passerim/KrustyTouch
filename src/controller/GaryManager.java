package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.SpongebobGame;

import javax.swing.Timer;

public class GaryManager extends Bonus implements  Runnable {
    
    private static final int BONUS_DURATION = 10000;
    private SpongebobGame model;
    
    public GaryManager(AnchorPane base, SpongebobGame model){
        super(base, model.getBonusDuration());
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/gary_finale.png").toString()));
        this.model = model;
    }

    @Override
    public void spawn() {
        this.image.setFitWidth(98.7);
        this.image.setFitHeight(83.3);
        this.image.setLayoutY(0);
        this.image.setLayoutX(this.randomPosition());
        this.image.setVisible(true);
        Platform.runLater(()->root.getChildren().add(this.image));
    }

    @Override
    public void action() {
        final int oldTime = this.model.getPlanktonTime();
        this.model.delayBonus(oldTime);
        final Timer timer = new Timer(BONUS_DURATION,(event)->this.model.delayBonus(oldTime));
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
