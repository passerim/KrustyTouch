package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import controller.SpongebobGameController;
import javafx.beans.value.ChangeListener;
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
        this.root = new AnchorPane();
        Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.PrimaryStage.setMaxHeight((ScreenSize.getHeight()*90)/100);
        this.PrimaryStage.setMaxWidth((this.PrimaryStage.getMaxHeight()*9)/16);
        this.PrimaryStage.setTitle(FRAME_NAME);
        this.PrimaryStage.centerOnScreen();
        this.PrimaryStage.setMaximized(false);
        this.PrimaryStage.setFullScreen(false);
        this.PrimaryStage.setOnCloseRequest(we->this.observer.quit());
        this.PrimaryStage.setResizable(true);
        this.PrimaryStage.setMinHeight(480);
        this.SetMenuBackground(PrimaryStage, ScreenSize);
    }
    
    private void SetMenuBackground(final Stage PrimaryStage, final Dimension ScreenSize) {
        this.scene = new Scene(root, (ScreenSize.getHeight()*90)/100/16*9, (ScreenSize.getHeight()*90)/100);
        final ImageView Background= new ImageView();
        Background.setImage(new Image(ClassLoader.getSystemResource("images/Menu_di_Gioco.png").toString()));
        Background.setVisible(true);
        Background.fitWidthProperty().bind(root.widthProperty());
        Background.fitHeightProperty().bind(root.heightProperty());
        Background.setPreserveRatio(false);
        Background.setSmooth(true);
        this.root.getChildren().add(Background);
        final ImageView PlayButton= new ImageView(new Image(ClassLoader.getSystemResource("images/play_button.png").toString()));
        PlayButton.setVisible(true);
        PlayButton.setManaged(false);
        PlayButton.layoutYProperty().bind(scene.heightProperty().divide(2));
        PlayButton.layoutXProperty().bind(scene.widthProperty().divide(6));
        PlayButton.fitHeightProperty().bind(scene.widthProperty().divide(1.5));
        PlayButton.fitWidthProperty().bind(scene.widthProperty().divide(1.5));
        PlayButton.setPreserveRatio(true);
        this.root.getChildren().add(PlayButton);
        PlayButton.setOnMouseClicked((event)->{
            PrimaryStage.setResizable(false);
            this.SetGameBackground(this.scene.getHeight(), this.scene.getWidth());
        });
        this.root.prefWidthProperty().bind(this.root.heightProperty().divide(16).multiply(9));
    }
    
    private void SetGameBackground(final double height, final double width) {
        this.root = new AnchorPane();
        this.scene = new Scene(this.root, width,height);
        final ImageView background = new ImageView(new Image(ClassLoader.getSystemResource("images/sfondo_FINALE.png").toString()));
        background.setPreserveRatio(false);
        background.setSmooth(true);
        background.setVisible(true);
        background.fitWidthProperty().bind(root.widthProperty());
        background.fitHeightProperty().bind(root.heightProperty());
        this.root.getChildren().add(background);
        this.score.setText("Score: 0");
        this.score.setFont(new Font("Arial", this.root.getHeight()/25));
        AnchorPane.setRightAnchor(this.score, 0.);
        AnchorPane.setTopAnchor(this.score, 0.);
        this.root.getChildren().add(this.score);
        this.root.addEventFilter(MouseEvent.DRAG_DETECTED, new SequencePainter(this.root, this.controller));
        try {
            this.observer.newGame(this.root);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        PrimaryStage.setScene(this.scene);
    }
    
    @Override
    public void setScore(final int val) {
        this.score.setText("Score: " + val);
    }

    @Override
    public void start(){
        this.PrimaryStage.setScene(scene);
        this.PrimaryStage.sizeToScene();
        this.PrimaryStage.show();
        System.out.println(root.getHeight() + "    " + root.getWidth());
        ChangeListener<Number> sceneSizeListener = (observable, oldValue, newValue) -> {
            if (observable.toString().contains("height")) {
                this.PrimaryStage.setWidth(newValue.doubleValue()/16*9);
                this.PrimaryStage.setMinWidth(PrimaryStage.getWidth());
                this.PrimaryStage.setMaxWidth(PrimaryStage.getWidth());
            }
        };
        if (System.getProperty("os.name").toLowerCase().contains("win") || System.getProperty("os.name").toLowerCase().contains("mac")) {
            this.scene.heightProperty().addListener(sceneSizeListener);
        } else {
            this.PrimaryStage.setMaxWidth(PrimaryStage.getWidth());
            this.PrimaryStage.setMinWidth(480/16*9);
        }
    }

    @Override
    public void setObserver(final SpongebobGameViewObserver observer){
        this.observer = observer;
    }

}
