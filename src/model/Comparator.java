package model;

import java.util.Optional;

/**
 * Interfacing for instantiating the Runnable that executes gesture recognition.
 */
public interface Comparator extends Runnable {

    /**
     * Get recognized symbol if any.
     * @return
     *          recognized symbol
     */
    Optional<RefModels> getValue();

    /**
     * Adds a point to be processed.
     * @param toX
     *          x coordinate of point
     * @param toY
     *          y coordinate of point
     */
    void add(int toX, int toY);
    
    @Override
    void run();

}
