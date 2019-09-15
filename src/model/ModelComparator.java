package model;

/**
 * Compares ref models with passed dirctions sequence, computes score and stores lower.
 */
public interface ModelComparator {
    
    /**
     * 
     * @return
     *          returns lower dtw cost
     */
    Integer getResult();

}
