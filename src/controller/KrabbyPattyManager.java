package controller;

import javax.swing.Timer;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class KrabbyPattyManager extends Bonus implements Runnable {
    
    private static final int BONUS_DURATION = 5000;
    private SpongebobGameController controller;

    public KrabbyPattyManager(final AnchorPane base, final SpongebobGameController controller){
        super(base, controller.getModel().getBonusDuration());
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/KrabbyPatty.png").toString()));
        this.controller = controller;
    }

    @Override
    public void spawn() {
        this.image.setFitHeight(this.root.getHeight()/11);
        this.image.setFitWidth(this.root.getWidth()/5.5);
        this.image.setLayoutY(0);
        this.image.setLayoutX(randomPosition());
        this.image.setVisible(true);
        Platform.runLater(()->root.getChildren().add(this.image));
    }

    @Override
    public void action() {
        this.controller.getModel().setScoreBonus();
        final Timer timer = new Timer(BONUS_DURATION,(event)->this.controller.getModel().setScoreBonus());
        timer.setRepeats(false);
        timer.start();
        Platform.runLater(()->root.getChildren().remove(this.image));
        System.out.println("Ciao");
    }

    @Override
    public void run() {
        spawn();
        move();
        this.image.setOnMouseClicked((event)->action());
    }
}
