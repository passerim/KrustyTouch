package controller;

import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Comparator;
import model.RefModels;
import model.SpongebobGame;
import model.SpongebobGameImpl;
import util.Pair;
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
     * @param primaryStage
     *          game stage
     */
    public SpongebobGameControllerImpl(final Stage primaryStage) {
        this.model = new SpongebobGameImpl();
        this.view = new SpongebobGameViewImpl(primaryStage, (SpongebobGameViewObserver) this);
        this.view.start();
    }

    private void startCharacters() {
        SpawnerPlanktonManager.getPlanktonSpawner(this).start();
        SpongebobManager.getSpongebobManager(this).start();
        BonusSpawner.getBonusSpawner(this).start();
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    @Override
    public void newGame() {
        this.startCharacters();
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

    @Override
    public void compare(final List<Pair<Integer, Integer>> points) {
        final Comparator cmp = this.model.getComparator();
        points.stream().forEach(p -> cmp.add(p.getX(), p.getY()));
        final Thread th = new Thread(cmp);
        th.start();
        if (th.getState() != Thread.State.RUNNABLE && th.getState() != Thread.State.TERMINATED) {
            th.start();
        }
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final Optional<RefModels> m = cmp.getValue();
        if (m.isPresent()) {
            if (this.model.canRemove(m.get())) {
                final Plankton p = this.model.getMap().get(m.get()).get(0);
                p.disable();
                p.stopTransition();
                this.model.getMap().get(m.get()).remove(p);
                Platform.runLater(() -> {
                    this.updateScore();
                    this.removeNode(p.getPlankton());
                });
            }
        }
    }

}
