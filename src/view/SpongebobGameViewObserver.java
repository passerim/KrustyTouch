package view;

import javafx.scene.layout.AnchorPane;

public interface SpongebobGameViewObserver {
    
        void newGame(AnchorPane root) throws IllegalAccessException;
	
	void quit();
}
