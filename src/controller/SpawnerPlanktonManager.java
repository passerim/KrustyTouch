package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import model.RefModels;

public class SpawnerPlanktonManager extends Thread {

    private AnchorPane root;
    private static SpawnerPlanktonManager SINGLETON = null;
    private SpongebobGameController controller;
    private Image[] images= new Image[2];
    
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
                final int n = this.Random_Selector();
                Thread.sleep(this.controller.getModel().getPlanktonRate());
                PlanktonManager plankton = new PlanktonManager(root, this.controller, this.images.clone());
                this.controller.getModel().addToMap(RefModels.values()[n], plankton);
                plankton.start();
                Platform.runLater(() -> {
                    this.root.getChildren().add(plankton.Plankton);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public int Random_Selector() {
        final int choice = (int) (Math.random()*9);
        switch (choice) {
        case 0: 
            images[0]= new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino2.png").toString());
            images[1]= new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino2.png").toString());
            break;
        case 1:
            images[0]= new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino3.png").toString());
            images[1]= new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino3.png").toString());
            break;
        case 2:
            images[0]= new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino4.png").toString());
            images[1]= new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino4.png").toString());
            break;
        case 3:
            images[0]= new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino5.png").toString());
            images[1]= new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino5.png").toString());
            break;
        case 4:
            images[0]= new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino6.png").toString());
            images[1]= new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino6.png").toString());
            break;
        case 5:
            images[0]= new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino7.png").toString());
            images[1]= new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino7.png").toString());
            break;       
        case 6:
            images[0]= new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino8.png").toString());
            images[1]= new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino8.png").toString());
            break;
        case 7:
            images[0]= new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino9.png").toString());
            images[1]= new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino9.png").toString());
            break;
        case 8:
            images[0]= new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino10.png").toString());
            images[1]= new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino10.png").toString());
            break;   
        default: 
            throw new IllegalStateException();      
        }
        return choice;
    }
    
}
