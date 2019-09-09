package view;

public interface SpongebobGameView {
	
	void setObserver(SpongebobGameViewObserver observer);
	
	void start(); 

	void numberIncorrect();

	void limitsReached();

	void result(/*DrawResult res*/);

}
