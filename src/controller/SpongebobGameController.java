package controller;

import model.SpongebobGame;

public interface SpongebobGameController {
    
    SpongebobGame getModel();
    
    void quit();
    
    void updateScore();

}
