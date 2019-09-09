package controller;

import javafx.scene.layout.AnchorPane;
import view.SpongebobGameViewObserver;

public class SpongebobGameControllerImpl implements SpongebobGameViewObserver {

    public SpongebobGameControllerImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void startCharacters(AnchorPane root) {
        //starting plankton
        SpawnerPlanktonManager.getPlanktonSpawner(root).start();
        //starting spongebob
        SpongebobManager.getSpongebobManager(root).start();
        //starting bonus spawner
        BonusSpawner.getBonusSpawner(root).start();

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

}
