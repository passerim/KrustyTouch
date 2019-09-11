package controller;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class SpawnerPlanktonManager extends Thread {

    private AnchorPane root;
    private ArrayList<PlanktonManager> planktonCollector = new ArrayList<>();
    private static SpawnerPlanktonManager SINGLETON = null;
    private SpongebobGameController controller;
    
    private SpawnerPlanktonManager(AnchorPane base, SpongebobGameController controller) {
        this.root = base;
        this.controller = controller;
    }

    public static synchronized SpawnerPlanktonManager getPlanktonSpawner(AnchorPane base, SpongebobGameController controller) {
        if (SINGLETON == null) {
            SINGLETON = new SpawnerPlanktonManager(base, controller);
        }
        return SINGLETON;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(this.controller.getModel().getPlanktonRate());
                PlanktonManager plankton = new PlanktonManager(root, this.controller.getModel().getPlanktonTime());
                plankton.start();
                Platform.runLater(() -> {
                    
                    root.getChildren().add(plankton.Plankton);
                    //planktonCollector.add(plankton);
                    
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public ArrayList<PlanktonManager> getPlanktonCollector() {
        return this.planktonCollector;
    }
    
    public void setPlanktonCollector(ArrayList<PlanktonManager> collector) {
        this.planktonCollector = collector;
    }
    
}
