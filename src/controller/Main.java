package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.SpongebobGameView;
import view.SpongebobGameViewImpl;
import view.SpongebobGameViewObserver;

public class Main extends Application {

    private SpongebobGameView view;
    private SpongebobGameViewObserver controller;
    
    @Override
    public void start(Stage PrimaryStage) {
        this.controller = new SpongebobGameControllerImpl();
        this.view = new SpongebobGameViewImpl(PrimaryStage, this.controller);
        this.view.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

