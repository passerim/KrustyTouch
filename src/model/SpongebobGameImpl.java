package model;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import controller.Plankton;

/**
 * Game model implementation.
 */
public class SpongebobGameImpl implements SpongebobGame {

    private static final double COEFF = 0.435;
    private static final int SCORE_MULTIPLIER = 5;
    private static final int DELAY_BONUS_RATE = 8000;
    private static final int HIGHSCORE = 150;
    private static final int MIN_RATE = 500;
    private static final int INIT_RATE = 2500;
    private Integer score = 0;
    private int bonusDuration;
    private int planktonDuration;
    private long bonusRate;
    private long planktonRate;
    private boolean delayBonus = false;
    private int scoreMultiplier = 1;
    private final Map<RefModels, List<Plankton>> map = new EnumMap<>(RefModels.class);
    private boolean scoreBonus = false;

    /**
     * 
     */
    public SpongebobGameImpl() {
        this.bonusDuration = 5000;
        this.bonusRate = 5000;
        this.planktonRate = INIT_RATE;
        this.planktonDuration = 5000;
        for (final RefModels m : RefModels.values()) {
            map.put(m, new LinkedList<>());
        }
    }

    @Override
    public Integer getScore() {
        return this.score;
    }

    @Override
    public long getBonusRate() {
        this.computeBonusRate();
        return this.bonusRate;
    }

    private void computeBonusRate() {
        this.bonusRate *= 1;
    }

    @Override
    public int getBonusDuration() {
        computeBonusDuration();
        return this.bonusDuration;
    }

    private void computeBonusDuration() {
        this.bonusDuration *= 1;
    }

    @Override
    public long getPlanktonRate() {
        computePlanktonRate();
        return this.planktonRate;
    }

    private void computePlanktonRate() {
        this.planktonRate = (int) (Math.exp((-Math.pow(this.score, 2)) / (COEFF * Math.pow(HIGHSCORE, 2))) 
                * (INIT_RATE - MIN_RATE)) + MIN_RATE;
    }

    @Override
    public int getPlanktonTime() {
        if (!this.delayBonus) {
            this.computePlanktonDuration();
        }
        return this.planktonDuration;
    }

    private void computePlanktonDuration() {
        this.planktonDuration *= 1;
    }

    @Override
    public boolean onDelayBonus() {
        if (!this.delayBonus) {
            this.delayBonus = true;
            this.planktonRate = DELAY_BONUS_RATE;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean offDelayBonus(final int oldTime) {
        if (this.delayBonus) {
            this.delayBonus = false;
            this.planktonRate = oldTime;
            return true;
        }
        return false;
    }

    @Override
    public void addToMap(final RefModels model, final Plankton plank) {
        this.map.get(model).add(plank);
    }

    @Override
    public Map<RefModels, List<Plankton>> getMap() {
        return this.map;
    }

    @Override
    public boolean canRemove(final RefModels model) {
        return !this.map.get(model).isEmpty();
    }

    @Override
    public void incrementScore() {
        this.score = this.score + 1 * this.scoreMultiplier;
    }

    @Override
    public void setScoreBonus() {
        if (!this.scoreBonus) {
            this.scoreBonus  = true;
            this.scoreMultiplier = SCORE_MULTIPLIER;
        } else {
            this.scoreBonus = false;
            this.scoreMultiplier = 1;
        }
    }
    
    @Override
    public void freeze() {
        this.map.entrySet().stream().flatMap(m -> m.getValue().stream()).forEach(p -> p.stopTransition());
    }

}
