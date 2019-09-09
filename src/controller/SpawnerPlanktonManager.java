package controller;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;

public class SpawnerPlanktonManager extends Thread {

    private AnchorPane root;
    private ArrayList<PlanktonManager> planktonCollector = new ArrayList<>();
    private int time=1000;
    private static SpawnerPlanktonManager SINGLETON = null;
    
    private SpawnerPlanktonManager(AnchorPane base) {
        this.root = base;
    }

    public static synchronized SpawnerPlanktonManager getPlanktonSpawner(AnchorPane base) {
        if (SINGLETON == null) {
            SINGLETON = new SpawnerPlanktonManager(base);
        }
        return SINGLETON;
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
    
    public ArrayList<PlanktonManager> getPlanktonCollector() {
        return this.planktonCollector;
    }
    
    public void setPlanktonCollector(ArrayList<PlanktonManager> collector) {
        this.planktonCollector = collector;
    }
    
    public void setTime(int t) {
        this.time = t;
    }
    
    public int getTime() {
        return this.time;
    }
}
