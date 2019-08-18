package application;


import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MrKrabManager implements Bonus,Runnable{
   ImageView MrKrab = new ImageView(new Image("Images/mrkrab_finale.png"));
   AnchorPane root;
   TranslateTransition movement = new TranslateTransition();
   public MrKrabManager(AnchorPane base){
       this.root=base;

   }

    @Override
    public void Spawn() {
        MrKrab.setFitWidth(74);
        MrKrab.setFitHeight(62.5);
        MrKrab.setLayoutY(0);
        MrKrab.setLayoutX(250);
        MrKrab.setVisible(true);
        Platform.runLater(()->root.getChildren().add(MrKrab));
    }

    public void action(){
        Main.SpawnerManager.planktonCollector.forEach(elem->this.root.getChildren().remove(elem.Plankton));
        System.out.println("Ciao");
        Platform.runLater(()->root.getChildren().remove(MrKrab));

        }

    @Override
    public void Move() {
        movement.setNode(MrKrab);
        movement.setDuration(Duration.millis(3000));
        movement.setFromY(0);
        movement.setToY((root.getHeight()*55)/100);
        movement.setOnFinished((event)-> root.getChildren().remove(this.MrKrab));
        movement.play();
    }

    @Override
    public void run() {
            Spawn();
            Move();
            MrKrab.setOnMouseClicked((event)->action());


    }
}
