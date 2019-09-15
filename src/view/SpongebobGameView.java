package view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

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
     * Sets new score in game view "score" label.
     * @param val
     *          New score.
     */
    void setScore(int val);

    /**
     * Returns main view node.
     * @return
     *          view's root node
     */
    Pane getRoot();

    /**
     * Adds children node to root node.
     * @param e
     *          node to be added
     */
    void addChildren(Node e);

    /**
     * Removes children node e from root node.
     * @param e
     *          node to be removed
     */
    void removeChildren(Node e);

}
