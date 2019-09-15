package controller;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
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
    
    /**
     * Return main view node from view.
     * @return
     *          view's root node
     */
    Pane getRoot();
    
    /**
     * Removes node from view's root node.
     * @param e
     *          node to be removed
     */
    void removeNode(Node e);
    
    /**
     * Adds node to view's root node.
     * @param e
     *          node to be added
     */
    void addNode(Node e);

}
