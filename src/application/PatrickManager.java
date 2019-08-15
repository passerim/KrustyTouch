package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PatrickManager implements Bonus {

   AnchorPane root;
   ImageView Patrick = new ImageView(new Image("Images/patrick_stella.png"));

    public PatrickManager(AnchorPane base){this.root=base;}

    @Override
    public void Spawn() {
        Patrick.setFitWidth(220);
        Patrick.setFitHeight(158);
        Patrick.setLayoutY(500);
        Patrick.setLayoutX(250);
        Patrick.setVisible(true);

    }

    @Override
    public void action() {

    }
}
