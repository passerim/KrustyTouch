package model;

import java.util.List;
import java.util.Map;

import controller.PlanktonManager;

public interface SpongebobGame {

    void setStartTime() throws IllegalAccessException;

    long getElapsedTime() throws IllegalAccessException;

    Integer getScore();

    long getBonusRate();

    int getBonusDuration();

    long getPlanktonRate();

    int getPlanktonTime();

    void addToMap(RefModels model, PlanktonManager plank);

    Map<RefModels, List<PlanktonManager>> getMap();

    boolean canRemove(RefModels model);

    void incrementScore();

    void setScoreBonus();

    void freeze();

    boolean offDelayBonus(int oldTime);

    boolean onDelayBonus();
}
