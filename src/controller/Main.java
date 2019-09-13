package controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(final Stage PrimaryStage) {
        new SpongebobGameControllerImpl(PrimaryStage);
    }

    public static void main(final String[] args) {
        launch(args);
    }
    
}

