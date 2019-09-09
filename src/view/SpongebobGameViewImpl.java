package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SpongebobGameViewImpl implements SpongebobGameView {

    private static final String FRAME_NAME = "Krusty Touch";
    
    static{
    }

    private SpongebobGameViewObserver observer;
    private AnchorPane root;
    private Stage PrimaryStage;

    public SpongebobGameViewImpl(Stage PrimaryStage, SpongebobGameViewObserver observer) {
        this.PrimaryStage = PrimaryStage;
        this.observer = observer;
        try {
            root = FXMLLoader.load(getClass().getResource("Scena.fxml"));
            Scene scene = new Scene(root);
            root.setMaxSize(500,1000);
            root.setMinSize(400,800);
            Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
            root.setMaxHeight((ScreenSize.getHeight()*90)/100);
            root.setMaxWidth((root.getMaxHeight()/2));
            root.setMinHeight((ScreenSize.getHeight()*40)/100);
            root.setMinWidth(root.getMinHeight()/2);
            root.setPrefHeight((ScreenSize.getHeight()*90)/100);
            root.setPrefWidth((root.getMaxHeight()/2));
            //starting the adaptive background image
            ImageView background = new ImageView(new Image("images/sfondo_FINALE.png"));
            background.setPreserveRatio(true);
            background.setSmooth(true);
            background.setVisible(true);
            background.fitWidthProperty().bind(root.widthProperty());
            background.fitHeightProperty().bind(root.heightProperty());
            root.getChildren().add(background);
            this.observer.startCharacters(root);
            this.PrimaryStage.setTitle(FRAME_NAME);
            this.PrimaryStage.setMaxHeight(((ScreenSize.getHeight()*90)/100)+39);
            this.PrimaryStage.setMaxWidth(this.PrimaryStage.getMaxHeight()/2);
            this.PrimaryStage.setMinHeight(((ScreenSize.getHeight()*40)/100)+16);
            this.PrimaryStage.setMinWidth(this.PrimaryStage.getMinHeight()/2);
            this.PrimaryStage.setResizable(true);
            this.PrimaryStage.centerOnScreen();
            this.PrimaryStage.setFullScreen(false);
            this.PrimaryStage.setMaximized(false);
            this.PrimaryStage.setOnCloseRequest(we->System.exit(0));
            this.PrimaryStage.setScene(scene);
            this.PrimaryStage.sizeToScene();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void start(){
        this.PrimaryStage.show();
    }

    private boolean confirmDialog(String question, String name){
        return false;
    }

    public void setObserver(SpongebobGameViewObserver observer){
        this.observer = observer;
    }

    public void numberIncorrect() {

    }

    public void limitsReached() {

    }

    public void result() {

    }
}
