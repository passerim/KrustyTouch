package application;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class PatrickManager implements Bonus, Runnable{

   AnchorPane root;
   ImageView Patrick = new ImageView(new Image("Images/patrickstella.png"));
   TranslateTransition movement = new TranslateTransition();

    public PatrickManager(AnchorPane base){this.root=base;}

    @Override
    public void Spawn() {

        Patrick.setPreserveRatio(true);
        Patrick.setFitWidth(110);
        Patrick.setFitHeight(79);
        Patrick.setLayoutY(0);
        Patrick.setLayoutX(250);
        Patrick.setVisible(true);
        Platform.runLater(()->root.getChildren().add(Patrick));
    }

    @Override
    public void action() {
        System.out.println("Ciao");
    }

    @Override
    public void Move() {
        movement.setNode(Patrick);
        movement.setDuration(Duration.millis(3000));
        movement.setFromY(0);
        movement.setToY((root.getHeight()*55)/100);
        movement.setOnFinished((event)-> root.getChildren().remove(this.Patrick));
        movement.play();
    }

    @Override
    public void run() {

            Spawn();
            Move();
            Patrick.setOnMouseClicked((event)->action());


    }
}
