package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GaryManager implements Bonus {

    AnchorPane root;
    ImageView Gary = new ImageView(new Image("Images/gary.png"));

    public GaryManager(AnchorPane base){
        this.root=base;
    }

    @Override
    public void Spawn() {
        Gary.setFitWidth(148);
        Gary.setFitHeight(125);
        Gary.setLayoutY(500);
        Gary.setLayoutX(250);
        Gary.setVisible(true);
    }

    @Override
    public void action() {

    }
}
