package application;



import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class MrKrabManager extends Bonus implements Runnable {
    
    public MrKrabManager(AnchorPane base){
        this.root=base;
        this.image = new ImageView(new Image("Images/mrkrab_finale.png"));
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
        Main.SpawnerManager.planktonCollector.forEach(elem->this.root.getChildren().remove(elem.Plankton));
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
