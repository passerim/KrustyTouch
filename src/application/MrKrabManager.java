package application;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MrKrabManager implements Bonus{
   ImageView MrKrab = new ImageView(new Image("Images/mrkrab.png"));
    AnchorPane root;
   public MrKrabManager(AnchorPane base){
       this.root=base;

   }

    @Override
    public void Spawn() {
        MrKrab.setFitWidth(148);
        MrKrab.setFitHeight(125);
        MrKrab.setLayoutY(500);
        MrKrab.setLayoutX(250);
        MrKrab.setVisible(true);

    }

    public void action(){
                Main.SpawnerManager.planktonCollector.forEach(elem->this.root.getChildren().remove(elem.Plankton));


        }
}
