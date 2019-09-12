package controller;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.SpongebobGame;

public class KrabbyPattyManager extends Bonus implements Runnable {
    
    private static final int BONUS_DURATION = 5000;
    private SpongebobGame model;

    public KrabbyPattyManager(final AnchorPane base, final SpongebobGame model){
        super(base, model.getBonusDuration());
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/KrabbyPatty.png").toString()));
        this.model = model;
    }

    @Override
    public void spawn() {
        this.image.setFitHeight(61);
        this.image.setFitWidth(61);
        this.image.setLayoutY(0);
        this.image.setLayoutX(randomPosition());
        this.image.setVisible(true);
        Platform.runLater(()->root.getChildren().add(this.image));
    }

    @Override
    public void action() {
        this.model.setScoreBonus();
        final Timer timer = new Timer(BONUS_DURATION,(event)->this.model.setScoreBonus());
        timer.setRepeats(false);
        timer.start();
        System.out.println("Ciao");
    }

    @Override
    public void run() {
        spawn();
        move();
        this.image.setOnMouseClicked((event)->action());

    }
}
