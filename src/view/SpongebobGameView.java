package view;

/**
 * Interface for the main view class.
 */
public interface SpongebobGameView {

    /**
     * Sets view observer.
     * @param observer
     *          A class that implements {@link SpongebobGameViewObserver}
     */
    void setObserver(SpongebobGameViewObserver observer);

    /**
     * Starts the game via controller.
     */
    void start(); 

    /**
     * Sets (int) val as new score in game view "score" label.
     * @param val
     *          New score.
     */
    void setScore(int val);

}
