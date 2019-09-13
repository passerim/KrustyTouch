package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.Bonus;
import controller.PlanktonManager;

public class SpongebobGameImpl implements SpongebobGame {

    private static final int DELAY_BONUS_RATE = 3000;
    private Optional<Long> startTime = Optional.empty();
    private Integer score = 0;
    private int bonusDuration;
    private int planktonDuration;
    private long bonusRate;
    private long planktonRate;
    private boolean delayBonus = false;
    private int scoreMultiplier = 1;
    private Map<RefModels, List<PlanktonManager>> map = new HashMap<>();
    private boolean scoreBonus = false;
    private final List<Bonus> bonuses = new LinkedList<>();

    {
        for (RefModels m : RefModels.values()) {
            map.put(m, new LinkedList<>());
        }
    }

    public SpongebobGameImpl() {
        this.bonusDuration = 4000;
        this.bonusRate = 500;
        this.planktonRate = 1000;
        this.planktonDuration = 5000;
    }

    @Override
    public Integer getScore() {
        return this.score;
    }

    @Override
    public void setStartTime() throws IllegalAccessException {
        if (this.startTime.isPresent()) {
            throw new IllegalAccessException();
        }
        this.startTime = Optional.of(System.currentTimeMillis());
    }

    @Override
    public long getElapsedTime() throws IllegalAccessException {
        if (this.startTime.isEmpty()) {
            throw new IllegalAccessException();
        }
        return ((System.currentTimeMillis() - this.startTime.get()) / 1000);
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
        this.planktonRate *= 1;
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
    public void delayBonus(final int oldTime) {
        if (!this.delayBonus) {
            this.delayBonus = true;
            this.planktonRate = DELAY_BONUS_RATE;
        } else {
            this.delayBonus = false;
            this.planktonRate = oldTime;
        }
    }

    @Override
    public void addToMap(final RefModels model, final PlanktonManager plank) {
        this.map.get(model).add(plank);
    }

    @Override
    public Map<RefModels, List<PlanktonManager>> getMap() {
        return this.map;
    }

    @Override
    public boolean canRemove(RefModels model) {
        if (this.map.get(model).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void incrementScore() {
        this.score = this.score + 1 * this.scoreMultiplier;
    }

    @Override
    public void setScoreBonus() {
        if (!this.scoreBonus) {
            this.scoreBonus  = true;
            this.scoreMultiplier = 5;
        } else {
            this.scoreBonus = false;
            this.scoreMultiplier = 1;
        }
    }
    
    @Override
    public void freeze() {
        this.map.entrySet().stream().flatMap(m->m.getValue().stream()).forEach(p->p.stopTransition());
        this.bonuses.stream().forEach(b->b.stopTransition());
    }

}
