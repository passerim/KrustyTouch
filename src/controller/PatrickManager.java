package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.SpongebobGame;

public class PatrickManager extends Bonus implements Runnable{
    
    private SpongebobGame model;

    public PatrickManager(AnchorPane base, final SpongebobGame model){
        super(base, model.getBonusDuration());
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/patrickstella.png").toString()));
        this.model = model;
    }

    @Override
    public void spawn() {
        this.image.setPreserveRatio(true);
        this.image.setFitWidth(110);
        this.image.setFitHeight(79);
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
