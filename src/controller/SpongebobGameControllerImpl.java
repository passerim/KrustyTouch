package controller;

import javafx.scene.layout.AnchorPane;
import model.SpongebobGame;
import model.SpongebobGameImpl;
import view.SpongebobGameViewObserver;

public class SpongebobGameControllerImpl implements SpongebobGameViewObserver, SpongebobGameController {
    
    private final SpongebobGame model;

    public SpongebobGameControllerImpl() {
        this.model = new SpongebobGameImpl();
    }

    private void startCharacters(final AnchorPane root) {
        SpawnerPlanktonManager.getPlanktonSpawner(root, this).start();
        SpongebobManager.getSpongebobManager(root).start();
        BonusSpawner.getBonusSpawner(root, this).start();

    }

    @Override
    public void quit() {
        System.exit(0);
    }

    @Override
    public void newGame(final AnchorPane root) throws IllegalAccessException {
        this.model.setStartTime();
        this.startCharacters(root);
    }

    @Override
    public SpongebobGame getModel() {
        return this.model;
    }

}
