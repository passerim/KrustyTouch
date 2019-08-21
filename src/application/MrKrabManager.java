package application;



import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class MrKrabManager extends Bonus implements Runnable {
   ImageView MrKrab = new ImageView(new Image("Images/mrkrab_finale.png"));
   AnchorPane root;

    public MrKrabManager(AnchorPane base){
       this.root=base;

    }

    public void Spawn() {
        MrKrab.setFitWidth(74);
        MrKrab.setFitHeight(62.5);
        MrKrab.setLayoutY(0);
        MrKrab.setLayoutX(RandomPosition());
        MrKrab.setVisible(true);
        Platform.runLater(()->root.getChildren().add(MrKrab));
    }

    public void action(){
        Main.SpawnerManager.planktonCollector.forEach(elem->this.root.getChildren().remove(elem.Plankton));
        System.out.println("Ciao");
        Platform.runLater(()->root.getChildren().remove(MrKrab));

        }

    @Override
    public void run() {
            Spawn();
            Move(MrKrab);
            MrKrab.setOnMouseClicked((event)->action());


    }
}
