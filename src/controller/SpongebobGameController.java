package controller;

import model.SpongebobGame;

/**
 * Controller interface.
 *
 */
public interface SpongebobGameController {
    
    /**
     * Gives other controller's threads a reference to the model.
     * @return
     *          Returns model to other controller's threads
     */
    SpongebobGame getModel();
    
    /**
     * Quits the game.
     */
    void quit();
    
    /**
     * Updates score via model and pass it to view to be displayed.
     */
    void updateScore();

}
