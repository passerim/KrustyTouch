package controller;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.SpongebobGame;
import model.SpongebobGameImpl;
import view.SpongebobGameView;
import view.SpongebobGameViewImpl;
import view.SpongebobGameViewObserver;

/**
 * Controller implementation.
 */
public class SpongebobGameControllerImpl implements SpongebobGameViewObserver, SpongebobGameController {
    
    private final SpongebobGame model;
    private final SpongebobGameView view;

    /**
     * 
     * @param primaryStage
     *          game stage
     */
    public SpongebobGameControllerImpl(final Stage primaryStage) {
        this.model = new SpongebobGameImpl();
        this.view = new SpongebobGameViewImpl(primaryStage, (SpongebobGameViewObserver) this);
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
    public void newGame(final AnchorPane root) {
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

    @Override
    public Pane getRoot() {
        return this.view.getRoot();
    }

    @Override
    public void removeNode(final Node e) {
        this.view.removeChildren(e);
    }

    @Override
    public void addNode(final Node e) {
        this.view.addChildren(e);
    }

}
