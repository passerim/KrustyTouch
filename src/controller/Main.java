package controller;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 
 */
public class Main extends Application {

    /**
     * JavaFX start method.
     */
    @Override
    public void start(final Stage primaryStage) {
        new SpongebobGameControllerImpl(primaryStage);
    }

    /**
     * 
     * @param args
     *          unused
     */
    public static void main(final String[] args) {
        launch(args);
    }
    
}

