package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PatrickManager extends Bonus implements Runnable{

    public PatrickManager(final AnchorPane base, final SpongebobGameController controller){
        super(base, controller.getModel().getBonusDuration());
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/patrickstella.png").toString()));
    }

    @Override
    public void spawn() {
        this.image.setFitHeight(this.root.getHeight()/10);
        this.image.setFitWidth(this.root.getWidth()/6);
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
