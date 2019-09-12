package controller;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.SpongebobGame;
import model.SpongebobGameImpl;
import view.SpongebobGameView;
import view.SpongebobGameViewImpl;
import view.SpongebobGameViewObserver;

public class SpongebobGameControllerImpl implements SpongebobGameViewObserver, SpongebobGameController {
    
    private final SpongebobGame model;
    private final SpongebobGameView view;

    public SpongebobGameControllerImpl(final Stage PrimaryStage) {
        this.model = new SpongebobGameImpl();
        this.view = new SpongebobGameViewImpl(PrimaryStage, (SpongebobGameViewObserver) this);
        this.view.start();
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

    @Override
    public void updateScore() {
        this.model.incrementScore();
        this.view.setScore(this.model.getScore());
    }

}
