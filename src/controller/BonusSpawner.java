package controller;

import javafx.scene.layout.AnchorPane;

import java.util.concurrent.TimeUnit;

public class BonusSpawner extends Thread {

    //this class randomly chooses between three types of bonus: Mr Krab, Patrick, Gary, and Krabby Patty
    private AnchorPane root;
    private static BonusSpawner SINGLETON = null;
    
    private BonusSpawner(AnchorPane base){
        this.root= base;
    }
    
    public static synchronized BonusSpawner getBonusSpawner(AnchorPane base) {
        if (SINGLETON == null) {
            SINGLETON = new BonusSpawner(base);
        }
        return SINGLETON;
    }

    public void run(){
        while(true) {
            try {
                TimeUnit.MILLISECONDS.sleep(250);
                RandomChoice();
            } catch (Exception e) {
                // fai qualcosa!
            }
        }
    }
    
    private void RandomChoice() {
        //System.out.println("selecting a random bonus to spawn");
        int bonusSelector = (int) (Math.random() * 4);
        //System.out.println(bonusSelector);
        switch (bonusSelector) {
        case 0:
            Thread MrKrab = new Thread(new MrKrabManager(root));
            MrKrab.start();
            break;
        case 1:
            Thread Patrick = new Thread(new PatrickManager(root));
            Patrick.start();
            break;
        case 2:
            Thread KrabbyPatty = new Thread(new KrabbyPattyManager(root));
            KrabbyPatty.start();
            break;
        case 3:
            Thread Gary = new Thread(new GaryManager(root));
            Gary.start();
            break;
        }
    }
}
