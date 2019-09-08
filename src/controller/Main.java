package controller;

import java.awt.Dimension;
import java.awt.Toolkit;

import controller.BonusSpawner;
import controller.SpawnerPlanktonManager;
import controller.SpongebobManager;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {

    public static SpawnerPlanktonManager SpawnerManager;
    public static SpongebobManager spongebob;
    public BonusSpawner bonus;
    public static AnchorPane root;
    @Override
    public void start(Stage PrimaryStage) {
        try {
            root= FXMLLoader.load(getClass().getResource("Scena.fxml"));
            root.setMaxSize(500,1000);
            root.setMinSize(400,800);
            Scene scene = new Scene(root);
            
            //starting the adaptive background image
            ImageView background = new ImageView(new Image("images/sfondo_FINALE.png"));

            background.setPreserveRatio(true);
            background.setSmooth(true);
            background.setVisible(true);
            background.fitWidthProperty().bind(root.widthProperty());
            background.fitHeightProperty().bind(root.heightProperty());

            //starting plankton
            SpawnerManager = new SpawnerPlanktonManager(root);
            SpawnerManager.start();


            //starting spongebob
            spongebob = SpongebobManager.getSpongebobManager(root);
            spongebob.start();

            //starting bonus spawner
            bonus=new BonusSpawner(root);
            bonus.start();

            
            Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
            root.setMaxHeight((ScreenSize.getHeight()*90)/100);
            root.setMaxWidth((root.getMaxHeight()/2));
            root.setMinHeight((ScreenSize.getHeight()*40)/100);
            root.setMinWidth(root.getMinHeight()/2);
            root.setPrefHeight((ScreenSize.getHeight()*90)/100);
            root.setPrefWidth((root.getMaxHeight()/2));
            PrimaryStage.setTitle("Krusty Touch");
            PrimaryStage.setMaxHeight(((ScreenSize.getHeight()*90)/100)+39);
            PrimaryStage.setMaxWidth(PrimaryStage.getMaxHeight()/2);
            PrimaryStage.setMinHeight(((ScreenSize.getHeight()*40)/100)+16);
            PrimaryStage.setMinWidth(PrimaryStage.getMinHeight()/2);
            PrimaryStage.setResizable(true);
            PrimaryStage.centerOnScreen();
            PrimaryStage.setFullScreen(false);
            PrimaryStage.setMaximized(false);
            PrimaryStage.setOnCloseRequest(we->System.exit(0));
            root.getChildren().add(background);
            PrimaryStage.setScene(scene);
            PrimaryStage.sizeToScene();
            PrimaryStage.show();

            root.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    double height= (double) newValue;
                    double NewHeight = ((scene.getHeight()/1000)*100);
                    double NewWidth = ((scene.getWidth()/500)*100);
                    spongebob.getSpongebob().setLayoutY((height*75)/100);
                    spongebob.getSpongebob().setFitHeight(227*NewHeight/100);
                    spongebob.getSpongebob().setFitWidth(188*NewWidth/100);
                    
                    //System.out.println(spongebob.getSpongebob().getFitHeight());
                    //System.out.println(spongebob.getSpongebob().getFitWidth());
                    System.out.println("altezza della finestra "+root.heightProperty().get());
                    System.out.println("larghezza della finestra "+root.widthProperty().get());
                    
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

