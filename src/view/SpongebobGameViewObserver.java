package view;

import java.util.List;
import util.Pair;

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
     * Starts {@link ComparatorThread} on argument points.
     * @param points
     *          list of points to be processed
     */
    void compare(List<Pair<Integer, Integer>> points);
    
}
