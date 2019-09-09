package view;

import javafx.scene.layout.AnchorPane;

public interface SpongebobGameViewObserver {
    
        void startCharacters(AnchorPane root);
	
	void newAttempt(int n);
	
	void resetGame();
	
	void quit();
}
