package controller;




import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;


public class SpawnerPlanktonManager extends Thread {

    AnchorPane root;
    ArrayList<PlanktonManager> planktonCollector = new ArrayList<>();
    int time=1000;


    public SpawnerPlanktonManager(AnchorPane base) {
        this.root = base;
    }

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(time);
                Platform.runLater(() -> {
                    PlanktonManager plankton = new PlanktonManager(root);
                    root.getChildren().add(plankton.Plankton);
                    planktonCollector.add(plankton);
                    plankton.start();


                });

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

        }


    }


}
