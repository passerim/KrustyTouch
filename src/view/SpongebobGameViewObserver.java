package view;

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
}
