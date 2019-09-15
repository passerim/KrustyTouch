package controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import model.RefModels;
/** this class-thread is an enemy spawner.
* it randomly choose the type of balloon, it spawns enemies and collect all necessary information.
*/
public class SpawnerPlanktonManager extends Thread {

    private AnchorPane root;
    private static SpawnerPlanktonManager SINGLETON = null;
    private SpongebobGameController controller;
    private Image[] images= new Image[2];
    private boolean bonus = false;
    private int cached = -1;
    
    private SpawnerPlanktonManager(final AnchorPane base, final SpongebobGameController controller) {
        this.root = base;
        this.controller = controller;
    }
    /**this is the singleton method to allow one only instance of it.
     * 
     * @param base AnchorPane root
     * @param controller SpongebobGameController
     * @return the instance of this class
     */
    public static synchronized SpawnerPlanktonManager getPlanktonSpawner(final AnchorPane base, final SpongebobGameController controller) {
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
                    this.root.getChildren().add(plankton.plankton);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
/**checks if bonus is active.
* @return returns true o false
*/
    public boolean onBonus() {
        if (!this.bonus) {
            this.bonus = true;
            this.cached = -1;
            return true;
        }
        return false;
    }
/**this method checks if bonus is deactivated.
 * @return true or false
 */
    public boolean offBonus() {
        if (this.bonus) {
           this.bonus = false;
           return true;
        }
        return false;
    }
    
    private int Random_Selector()  {
        int choice = (int) (Math.random()*9);
        if (this.bonus) {
            if (this.cached == -1) {
                this.cached = choice;
            } else {
                choice = this.cached;
            }
        }
        switch (choice) {
        case 0: 
            images[0] = new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino2.png").toString());
            images[1] = new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino2.png").toString());
            break;
        case 1: 
            images[0] = new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino3.png").toString());
            images[1] = new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino3.png").toString());
            break;
        case 2:
            images[0] = new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino4.png").toString());
            images[1] = new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino4.png").toString());
            break;
        case 3: 
            images[0] = new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino5.png").toString());
            images[1] = new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino5.png").toString());
            break;
        case 4:
            images[0] = new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino6.png").toString());
            images[1] = new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino6.png").toString());
            break;
        case 5:
            images[0] = new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino7.png").toString());
            images[1] = new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino7.png").toString());
            break;       
        case 6:
            images[0] = new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino8.png").toString());
            images[1] = new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino8.png").toString());
            break;
        case 7:
            images[0] = new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino9.png").toString());
            images[1] = new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino9.png").toString());
            break;
        case 8:
            images[0] = new Image(ClassLoader.getSystemResource("images/plankton1_e_palloncino10.png").toString());
            images[1] = new Image(ClassLoader.getSystemResource("images/plankton2_e_palloncino10.png").toString());
            break;   
        default: 
            throw new IllegalStateException();      
        }
        return choice;
    }
    
}
