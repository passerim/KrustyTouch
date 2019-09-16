package controller;

import javafx.scene.image.ImageView;

/**
 * Interface for interacting with planktons.
 */
public interface Plankton extends Runnable {
    
    /**
     * 
     * @return
     *          plankton image
     */
    ImageView getPlankton();
    
    /**
     * Stops plankton transition.
     */
    void stopTransition();

    /**
     * This method disables and stop thread.
     */
    void disable();

}
