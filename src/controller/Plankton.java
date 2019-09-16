package controller;

import javafx.scene.image.ImageView;

/**
 * Interface for interacting with planktons.
 */
public interface Plankton {
    
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

}
