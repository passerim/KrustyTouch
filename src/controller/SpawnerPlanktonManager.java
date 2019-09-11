package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;

public class SpawnerPlanktonManager extends Thread {

    private AnchorPane root;
    private ArrayList<PlanktonManager> planktonCollector = new ArrayList<>();
    private int time=1000;
    private static SpawnerPlanktonManager SINGLETON = null;
    private Image[] images= new Image[2];
    
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
                Random_Selector();
                Platform.runLater(() -> {
                    PlanktonManager plankton = new PlanktonManager(root, images);
                    root.getChildren().add(plankton.Plankton);
                    planktonCollector.add(plankton);
                    plankton.start();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void Random_Selector() {
    	int choise = (int) (Math.random()*6);
    	switch (choise) {
    		
    	case 1: images[0]= new Image(this.getClass().getResource("../res/images/plankton2_e_palloncino1.png"));
    			
    			//class.getClassLoader().getResource("../res/images/plankton1_e_palloncino1.png"));
    			images[1]= new Image("../res/images/plankton2_e_palloncino1.png");
    			break;
    	case 2: images[0]= new Image("/res/images/plankton1_e_palloncino2.png");
				images[1]= new Image("/res/images/plankton2_e_palloncino2.png");
				break;
    	case 3:images[0]= new Image("/res/images/plankton1_e_palloncino3.png");
			   images[1]= new Image("/res/images/plankton2_e_palloncino3.png");
			   break;	
    	case 4:images[0]= new Image("/res/images/plankton1_e_palloncino4.png");
			   images[1]= new Image("/res/images/plankton2_e_palloncino4.png");
			   break;
    	case 5:images[0]= new Image("../res/images/plankton1_e_palloncino5.png");
			   images[1]= new Image("../res/images/plankton2_e_palloncino5.png");
			   break;
    	case 6:images[0]= new Image("../res/images/plankton1_e_palloncino6.png");
			   images[1]= new Image("../res/images/plankton2_e_palloncino6.png");
			   break;
    	case 7:images[0]= new Image("../res/images/plankton1_e_palloncino7.png");
		   	   images[1]= new Image("../res/images/plankton2_e_palloncino7.png");
		   break;	   
		default: System.out.println("error encoutered during the random choosing of the plankton");	   
    	
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
