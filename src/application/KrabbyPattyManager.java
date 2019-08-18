package application;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class KrabbyPattyManager implements Bonus, Runnable {

    AnchorPane root;
    ImageView KrabbyPatty= new ImageView(new Image("Images/KrabbyPatty.png"));
    TranslateTransition movement = new TranslateTransition();

    public KrabbyPattyManager(AnchorPane base){
        this.root=base;
    }

    @Override
    public void Spawn() {
        KrabbyPatty.setFitHeight(61);
        KrabbyPatty.setFitWidth(61);
        KrabbyPatty.setLayoutY(0);
        KrabbyPatty.setLayoutX(250);
        KrabbyPatty.setVisible(true);
        Platform.runLater(()->root.getChildren().add(KrabbyPatty));

    }

    @Override
    public void action() {
        System.out.println("Ciao");
    }

    @Override
    public void Move() {
        movement.setNode(KrabbyPatty);
        movement.setDuration(Duration.millis(3000));
        movement.setFromY(0);
        movement.setToY((root.getHeight()*55)/100);
        movement.setOnFinished((event)-> root.getChildren().remove(this.KrabbyPatty));
        movement.play();
    }

    @Override
    public void run() {
            Spawn();
            Move();
            KrabbyPatty.setOnMouseClicked((event)->action());

    }
}
