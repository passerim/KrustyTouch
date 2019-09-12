package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MrKrabManager extends Bonus implements Runnable {

    private SpongebobGameController controller;

    public MrKrabManager(AnchorPane base, final SpongebobGameController controller){
        super(base, controller.getModel().getBonusDuration());
        this.image = new ImageView(new Image(ClassLoader.getSystemResource("images/mrkrab_finale.png").toString()));
        this.controller = controller;
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
        this.controller.getModel().getMap().entrySet().stream().flatMap(e->e.getValue().stream()).forEach(elem->{
            this.root.getChildren().remove(elem.Plankton);
            elem.stop();
            elem.stopTransition();
        });
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
