package controller;

import javafx.scene.layout.AnchorPane;

import java.util.concurrent.TimeUnit;

public class BonusSpawner extends Thread {

    //this class randomly chooses between three types of bonus: Mr Krab, Patrick, Gary, and Krabby Patty
    private AnchorPane root;
    private static BonusSpawner SINGLETON = null;
    private final SpongebobGameController controller;
    
    private BonusSpawner(AnchorPane base, SpongebobGameController controller){
        this.root = base;
        this.controller = controller;
    }
    
    public static synchronized BonusSpawner getBonusSpawner(AnchorPane base, SpongebobGameController controller) {
        if (SINGLETON == null) {
            SINGLETON = new BonusSpawner(base, controller);
        }
        return SINGLETON;
    }

    public void run(){
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(this.controller.getModel().getBonusRate());
                this.RandomChoice();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void RandomChoice() {
        int bonusSelector = (int) (Math.random() * 4);
        switch (bonusSelector) {
        case 0:
            Thread MrKrab = new Thread(new MrKrabManager(root, this.controller.getModel().getBonusDuration()));
            MrKrab.start();
            break;
        case 1:
            Thread Patrick = new Thread(new PatrickManager(root, this.controller.getModel().getBonusDuration()));
            Patrick.start();
            break;
        case 2:
            Thread KrabbyPatty = new Thread(new KrabbyPattyManager(root, this.controller.getModel().getBonusDuration()));
            KrabbyPatty.start();
            break;
        case 3:
            Thread Gary = new Thread(new GaryManager(root, this.controller.getModel()));
            Gary.start();
            break;
        }
    }
}
