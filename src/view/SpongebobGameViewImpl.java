package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import controller.SpongebobGameController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SpongebobGameViewImpl implements SpongebobGameView {

    private static final String FRAME_NAME = "Krusty Touch";
    private SpongebobGameViewObserver observer;
    private AnchorPane root;
    private final Stage PrimaryStage;
    private Scene scene;
    private final SpongebobGameController controller;
    private final Label score = new Label();

    public SpongebobGameViewImpl(final Stage PrimaryStage, final SpongebobGameViewObserver observer) {
        this.controller = (SpongebobGameController) observer;
        this.PrimaryStage = PrimaryStage;
        this.observer = observer;
        try {
            root = new AnchorPane();
            final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
            scene = new Scene(root, (ScreenSize.getHeight()*90)/100/16*9, (ScreenSize.getHeight()*90)/100);
            final ImageView background = new ImageView(new Image("images/sfondo_FINALE.png"));
            background.setPreserveRatio(false);
            background.setSmooth(true);
            background.setVisible(true);
            background.fitWidthProperty().bind(root.widthProperty());
            background.fitHeightProperty().bind(root.heightProperty());
            this.score.setText("Score: 0");
            this.score.prefHeight(this.root.getHeight()/20);
            this.score.prefWidth(this.root.getHeight()/20/9*16);
            this.score.setFont(new Font("Arial", this.root.getHeight()/25));
            root.getChildren().add(background);
            root.getChildren().add(this.score);
            AnchorPane.setRightAnchor(this.score, 0.);
            AnchorPane.setTopAnchor(this.score, 0.);
            root.addEventFilter(MouseEvent.DRAG_DETECTED, new SequencePainter(root, this.controller));
            this.observer.newGame(root);
            this.PrimaryStage.setTitle(FRAME_NAME);
            this.PrimaryStage.setResizable(false);
            this.PrimaryStage.centerOnScreen();
            this.PrimaryStage.setFullScreen(false);
            this.PrimaryStage.setMaximized(false);
            this.PrimaryStage.setOnCloseRequest(we->this.observer.quit());
            //this.PrimaryStage.setScene(scene);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setScore(final int val) {
        this.score.setText("Score: " + val);
    }

    @Override
    public void start(){
        this.PrimaryStage.setScene(scene);
        this.PrimaryStage.show();
        System.out.println(root.getHeight() + "    " + root.getWidth());
    }

    @Override
    public void setObserver(SpongebobGameViewObserver observer){
        this.observer = observer;
    }

}
