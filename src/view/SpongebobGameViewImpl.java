package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import controller.SpongebobGameController;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SpongebobGameViewImpl implements SpongebobGameView {

    private static final String FRAME_NAME = "Krusty Touch";
    
    static{
    }

    private SpongebobGameViewObserver observer;
    private AnchorPane root;
    private Stage PrimaryStage;
    private Scene scene;
    private SpongebobGameController controller;

    public SpongebobGameViewImpl(Stage PrimaryStage, SpongebobGameViewObserver observer) {
        this.controller = (SpongebobGameController) observer;
        this.PrimaryStage = PrimaryStage;
        this.observer = observer;
        try {
            root = new AnchorPane();
            Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
            scene = new Scene(root, (ScreenSize.getHeight()*90)/100/16*9, (ScreenSize.getHeight()*90)/100);
            ImageView background = new ImageView(new Image("images/sfondo_FINALE.png"));
            background.setPreserveRatio(false);
            background.setSmooth(true);
            background.setVisible(true);
            background.fitWidthProperty().bind(root.widthProperty());
            background.fitHeightProperty().bind(root.heightProperty());
            root.getChildren().add(background);
            root.addEventFilter(MouseEvent.DRAG_DETECTED, new SequencePainter(root, this.controller));
            this.observer.newGame(root);
            this.PrimaryStage.setTitle(FRAME_NAME);
            this.PrimaryStage.setResizable(false);
            this.PrimaryStage.centerOnScreen();
            this.PrimaryStage.setFullScreen(false);
            this.PrimaryStage.setMaximized(false);
            this.PrimaryStage.setOnCloseRequest(we->System.exit(0));
            this.PrimaryStage.setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void start(){
        this.PrimaryStage.show();
        System.out.println(root.getHeight() + "    " + root.getWidth());
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
