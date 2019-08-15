package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class KrabbyPattyManager implements Bonus {

    AnchorPane root;
    ImageView KrabbyPatty= new ImageView(new Image("Images/krabby_patty.jpg"));

    public KrabbyPattyManager(AnchorPane base){
        this.root=base;
    }

    @Override
    public void Spawn() {
        KrabbyPatty.setFitHeight(224);
        KrabbyPatty.setFitWidth(224);
        KrabbyPatty.setLayoutY(500);
        KrabbyPatty.setLayoutX(250);
        KrabbyPatty.setVisible(true);
    }

    @Override
    public void action() {

    }
}
