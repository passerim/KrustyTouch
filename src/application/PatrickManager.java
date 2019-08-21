package application;


import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class PatrickManager extends Bonus implements Runnable{

   AnchorPane root;
   ImageView Patrick = new ImageView(new Image("Images/patrickstella.png"));


    public PatrickManager(AnchorPane base){this.root=base;}

    @Override
    public void Spawn() {

        Patrick.setPreserveRatio(true);
        Patrick.setFitWidth(110);
        Patrick.setFitHeight(79);
        Patrick.setLayoutY(0);
        Patrick.setLayoutX(RandomPosition());
        Patrick.setVisible(true);
        Platform.runLater(()->root.getChildren().add(Patrick));
    }

    @Override
    public void action() {
        System.out.println("Ciao");
    }


    @Override
    public void run() {

            Spawn();
            Move(Patrick);
            Patrick.setOnMouseClicked((event)->action());


    }
}
