package application;


import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class KrabbyPattyManager extends Bonus implements Runnable {

    AnchorPane root;
    ImageView KrabbyPatty= new ImageView(new Image("Images/KrabbyPatty.png"));


    public KrabbyPattyManager(AnchorPane base){
        this.root=base;
    }

    @Override
    public void Spawn() {
        KrabbyPatty.setFitHeight(61);
        KrabbyPatty.setFitWidth(61);
        KrabbyPatty.setLayoutY(0);
        KrabbyPatty.setLayoutX(RandomPosition());
        KrabbyPatty.setVisible(true);
        Platform.runLater(()->root.getChildren().add(KrabbyPatty));

    }

    @Override
    public void action() {
        System.out.println("Ciao");
    }

    @Override
    public void run() {
            Spawn();
            Move(KrabbyPatty);
            KrabbyPatty.setOnMouseClicked((event)->action());

    }
}
