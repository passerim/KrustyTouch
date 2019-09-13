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
        this.image.setFitHeight(this.root.getHeight()/10);
        this.image.setFitWidth(this.root.getWidth()/5);
        this.image.setLayoutY(0);
        this.image.setLayoutX(randomPosition());
        this.image.setVisible(true);
        Platform.runLater(()->root.getChildren().add(this.image));
    }

    @Override
    public void action(){
        this.controller.getModel().getMap().entrySet().stream().flatMap(e->e.getValue().stream()).forEach(elem->{
            this.root.getChildren().remove(elem.Plankton);
            this.controller.updateScore();
            elem.stop();
            elem.stopTransition();
        });
        this.controller.getModel().getMap().entrySet().stream().map(e->e.getValue()).forEach(l->l.clear());
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
