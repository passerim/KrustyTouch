package view;

import javafx.scene.layout.AnchorPane;

/**
 * This is an interface implemented by the controller, to allow controller-view communication.
 */
public interface SpongebobGameViewObserver {
    
    /**
     * Starts a new game, loading spawner threads and setting up the model.
     * @param root
     *          Root pane of the game scene
     * @throws IllegalAccessException
     *          If game is bad started
     */
    void newGame(AnchorPane root);

    /**
     * Game is quit.
     */
    void quit();
}
