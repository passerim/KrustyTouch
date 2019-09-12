package view;

public interface SpongebobGameView {

    void setObserver(SpongebobGameViewObserver observer);

    void start(); 

    void setScore(int val);

}
