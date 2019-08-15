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
                    MrKrabManager MrKrab = new MrKrabManager(root);
                    root.getChildren().add(MrKrab.MrKrab);
                    MrKrab.Spawn();
                    break;
                case 1:
                    PatrickManager Patrick = new PatrickManager(root);
                    Patrick.Spawn();
                    root.getChildren().add(Patrick.Patrick);

                    break;
                case 2:
                    KrabbyPattyManager KrabbyPatty = new KrabbyPattyManager(root);
                    KrabbyPatty.Spawn();
                    root.getChildren().add(KrabbyPatty.KrabbyPatty);

                    break;
                case 3:
                    GaryManager Gary = new GaryManager(root);
                    root.getChildren().add(Gary.Gary);
                    Gary.Spawn();
                    break;
            }
    }
}
