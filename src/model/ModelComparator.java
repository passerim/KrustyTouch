package model;

/**
 * Compares reference models with passed directions sequence, computes score and stores lower.
 */
public interface ModelComparator {
    
    /**
     * 
     * @return
     *          returns lower dtw cost
     */
    Integer getResult();

}
