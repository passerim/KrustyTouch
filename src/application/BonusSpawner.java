package application;

import javafx.scene.layout.AnchorPane;

import static java.lang.Thread.*;

public class BonusSpawner extends Thread {

    //this class randomly chooses between three types of bonus: Mr Krab, Patrick, Gary, and Krabby Patty
    AnchorPane root;
    public BonusSpawner(AnchorPane base){
        this.root= base;
    }

    
    public void run(){
        while(true) {
            try {
                sleep(5000);
                RandomChoice();
            } catch (Exception e) {

            }
        }
        
        
    }
    public void RandomChoice() {
        System.out.println("selecting a random bonus to spawn");
            int bonusSelector = (int) (Math.random() * 4);
        System.out.println(bonusSelector);
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
