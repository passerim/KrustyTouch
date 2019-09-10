package model;

public interface SpongebobGame {
	
        void setStartTime() throws IllegalAccessException;
        
        long getElapsedTime() throws IllegalAccessException;
    
	void reset();
	
	DrawResult attempt(int n) throws AttemptsLimitReachedException;

        void addPoints(Integer diffPoints);

        Integer getPoints();

        long getBonusRate();

        int getBonusDuration();

        long getPlanktonRate();

        int getPlanktonTime();

        void delayBonus(int oldTime);
}
