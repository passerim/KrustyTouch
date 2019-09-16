package view;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.Pair;

/**
 * This is an interface implemented by the controller, to allow controller-view communication.
 */
public interface SpongebobGameViewObserver {
    
    /**
     * Starts a new game, loading spawner threads and setting up the model.
     * @throws IllegalAccessException
     *          If game is bad started
     */
    void newGame();

    /**
     * Game is quit.
     */
    void quit();
   
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

    void compare(List<Pair<Integer, Integer>> points);
    
}
