package model;

import java.util.List;
import java.util.Map;

import controller.PlanktonManager;

public interface SpongebobGame {

    void setStartTime() throws IllegalAccessException;

    long getElapsedTime() throws IllegalAccessException;

    void reset();

    Integer getScore();

    long getBonusRate();

    int getBonusDuration();

    long getPlanktonRate();

    int getPlanktonTime();

    void delayBonus(int oldTime);

    void addToMap(RefModels model, PlanktonManager plank);

    Map<RefModels, List<PlanktonManager>> getMap();

    boolean canRemove(RefModels model);

    void incrementScore();

    void setScoreBonus();
}
