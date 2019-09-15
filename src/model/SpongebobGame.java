package model;

import java.util.List;
import java.util.Map;

import controller.PlanktonManager;

/**
 * Interface for game's model.
 */
public interface SpongebobGame {

    /**
     * 
     * @return
     *          current score
     */
    Integer getScore();

    /**
     * 
     * @return
     *          current bonus rate
     */
    long getBonusRate();

    /**
     * 
     * @return 
     *          current bonus duration
     */
    int getBonusDuration();

    /**
     * 
     * @return
     *          current plankton spawn rate
     */
    long getPlanktonRate();

    /**
     * 
     * @return
     *          current plankton duration
     */
    int getPlanktonTime();

    /**
     * 
     * @param model
     *          {@link RefModels} to be added to
     * @param plank
     *          {@link PlanktonManager} plankton
     */
    void addToMap(RefModels model, PlanktonManager plank);

    /**
     * 
     * @return
     *          on screen planktons map
     */
    Map<RefModels, List<PlanktonManager>> getMap();

    /**
     * 
     * @param model
     *          {@link RefModels} to be removed from
     * @return
     *          weather are planktons on screen of required model
     */
    boolean canRemove(RefModels model);

    /**
     * Increments current score.
     */
    void incrementScore();

    /**
     * Sets score bonus.
     */
    void setScoreBonus();

    /**
     * Freezes game.
     */
    void freeze();

    /**
     * 
     * @param oldTime
     *          old plankton rate
     * @return
     *          weather this bonus was disabled up or not
     */
    boolean offDelayBonus(int oldTime);

    /**
     * 
     * @return
     *          weather this bonus was set up or not
     */
    boolean onDelayBonus();
}
