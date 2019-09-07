package controller;


import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class KrabbyPattyManager extends Bonus implements Runnable {

    public KrabbyPattyManager(AnchorPane base){
        this.root=base;
        this.image = new ImageView(new Image("Images/KrabbyPatty.png"));
    }

    @Override
    public void spawn() {
        this.image.setFitHeight(61);
        this.image.setFitWidth(61);
        this.image.setLayoutY(0);
        this.image.setLayoutX(randomPosition());
        this.image.setVisible(true);
        Platform.runLater(()->root.getChildren().add(this.image));

    }

    @Override
    public void action() {
        System.out.println("Ciao");
    }

    @Override
    public void run() {
        spawn();
        move();
        this.image.setOnMouseClicked((event)->action());

    }
}
