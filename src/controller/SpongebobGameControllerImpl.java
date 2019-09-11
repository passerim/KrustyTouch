package controller;

import javafx.scene.layout.AnchorPane;
import model.SpongebobGame;
import model.SpongebobGameImpl;
import view.SpongebobGameViewObserver;

public class SpongebobGameControllerImpl implements SpongebobGameViewObserver, SpongebobGameController {
    
    private SpongebobGame model;

    public SpongebobGameControllerImpl() {
        this.model = new SpongebobGameImpl();
    }

    private void startCharacters(AnchorPane root) {
        //starting plankton
        SpawnerPlanktonManager.getPlanktonSpawner(root, this).start();
        //starting spongebob
        SpongebobManager.getSpongebobManager(root).start();
        //starting bonus spawner
        BonusSpawner.getBonusSpawner(root, this).start();

    }

    @Override
    public void newAttempt(int n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void resetGame() {
        // TODO Auto-generated method stub

    }

    @Override
    public void quit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void newGame(AnchorPane root) throws IllegalAccessException {
        this.model.setStartTime();
        this.startCharacters(root);
    }

    @Override
    public SpongebobGame getModel() {
        return this.model;
    }

}
