package application;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javax.swing.Timer;

public class GaryManager implements Bonus, Runnable {

    AnchorPane root;
    ImageView Gary = new ImageView(new Image("Images/gary_finale.png"));
    TranslateTransition movement = new TranslateTransition();

    public GaryManager(AnchorPane base){
        this.root=base;
    }

    @Override
    public void Spawn() {
        Gary.setFitWidth(98.7);
        Gary.setFitHeight(83.3);
        Gary.setLayoutY(0);
        Gary.setLayoutX(250);
        Gary.setVisible(true);
        Platform.runLater(()->root.getChildren().add(Gary));
    }

    @Override
    public void action() {
            Main.SpawnerManager.time=10000;
            Timer timer = new Timer(200000,(event)->Main.SpawnerManager.time=1000);
            timer.setRepeats(false);
            timer.start();
        Platform.runLater(()->root.getChildren().remove(Gary));
        System.out.println("Ciao");
    }

    @Override
    public void Move() {
        movement.setNode(Gary);
        movement.setDuration(Duration.millis(3000));
        movement.setFromY(0);
        movement.setToY((root.getHeight()*55)/100);
        movement.setOnFinished((event)-> root.getChildren().remove(this.Gary));
        movement.play();
    }

    @Override
    public void run() {
            Spawn();
            Move();
            this.Gary.setOnMouseClicked((event)->action());
    }
}
