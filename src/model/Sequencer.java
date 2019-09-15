package model;

/**
 * This class computes the sequence of directions associated with input data points.
 */
public interface Sequencer {
    
    /**
     * Computes sequence.
     * @return
     *          array of integers containing directions from input data
     */
    Integer[] computeSequence();

}
