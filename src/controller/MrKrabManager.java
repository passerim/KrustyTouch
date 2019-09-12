package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.SpongebobGame;

public class MrKrabManager extends Bonus implements Runnable {
    
    private SpongebobGame model;

    public MrKrabManager(AnchorPane base, final SpongebobGame model){
        super(base, model.getBonusDuration());
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/mrkrab_finale.png").toString()));
        this.model = model;
    }

    @Override
    public void spawn() {
        this.image.setFitWidth(74);
        this.image.setFitHeight(62.5);
        this.image.setLayoutY(0);
        this.image.setLayoutX(randomPosition());
        this.image.setVisible(true);
        Platform.runLater(()->root.getChildren().add(this.image));
    }

    @Override
    public void action(){
        model.getMap().entrySet().stream().flatMap(e->e.getValue().stream()).forEach(elem->this.root.getChildren().remove(elem.Plankton));
        System.out.println("Ciao");
        Platform.runLater(()->root.getChildren().remove(this.image));
    }

    @Override
    public void run() {
        spawn();
        move();
        this.image.setOnMouseClicked((event)->action());
    }

}
