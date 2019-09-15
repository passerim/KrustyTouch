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

	/**
	 * This class has the duty of managing the view of the game,
	 * which involves menu and game background.
	 */
public class SpongebobGameViewImpl implements SpongebobGameView {

    private static final String FRAME_NAME = "Krusty Touch";
    private static final double ASPECT_RATIO = (9. / 16.);
    private static final double DEFAULT_HEIGHT_TO_SCREEN_RATIO = (90. / 100.);
    private static final int MINIMUM_SUPPORTED_HEIGHT_RES = 480;
    private static final int FONT_SIZE = 25;
    private SpongebobGameViewObserver observer;
    private AnchorPane root;
    private final Stage PrimaryStage;
    private Scene scene;
    private final SpongebobGameController controller;
    private final Label score = new Label();
    /** this is the constructor method, which initiates the menu, first, and the game background right after.
     * 
     * @param PrimaryStage PrimaryStage is the window
     * @param observer SpongebobGameViewObserver 
     */
    public SpongebobGameViewImpl(final Stage PrimaryStage, final SpongebobGameViewObserver observer) {
        this.controller = (SpongebobGameController) observer;
        this.PrimaryStage = PrimaryStage;
        this.observer = observer;
        this.root = new AnchorPane();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.PrimaryStage.setMaxHeight(screenSize.getHeight() * DEFAULT_HEIGHT_TO_SCREEN_RATIO);
        this.PrimaryStage.setMaxWidth(this.PrimaryStage.getMaxHeight() * ASPECT_RATIO);
        this.PrimaryStage.setTitle(FRAME_NAME);
        this.PrimaryStage.centerOnScreen();
        this.PrimaryStage.setMaximized(false);
        this.PrimaryStage.setFullScreen(false);
        this.PrimaryStage.setOnCloseRequest(we -> this.observer.quit());
        this.PrimaryStage.setResizable(true);
        this.PrimaryStage.setMinHeight(MINIMUM_SUPPORTED_HEIGHT_RES);
        this.setMenuBackground(PrimaryStage, screenSize);
    }

    private void setMenuBackground(final Stage PrimaryStage, final Dimension screenSize) {
        this.scene = new Scene(root, (screenSize.getHeight() * DEFAULT_HEIGHT_TO_SCREEN_RATIO * ASPECT_RATIO), screenSize.getHeight() * DEFAULT_HEIGHT_TO_SCREEN_RATIO);
        final ImageView background = new ImageView();
        background.setImage(new Image(ClassLoader.getSystemResource("images/Menu_di_Gioco.png").toString()));
        background.setVisible(true);
        background.fitWidthProperty().bind(root.widthProperty());
        background.fitHeightProperty().bind(root.heightProperty());
        background.setPreserveRatio(false);
        background.setSmooth(true);
        this.root.getChildren().add(background);
        final ImageView playButton = new ImageView(new Image(ClassLoader.getSystemResource("images/play_button.png").toString()));
        playButton.setVisible(true);
        playButton.setManaged(false);
        playButton.layoutYProperty().bind(scene.heightProperty().divide(2));
        playButton.layoutXProperty().bind(scene.widthProperty().divide(6));
        playButton.fitHeightProperty().bind(scene.widthProperty().divide(1.5));
        playButton.fitWidthProperty().bind(scene.widthProperty().divide(1.5));
        playButton.setPreserveRatio(true);
        this.root.getChildren().add(playButton);
        playButton.setOnMouseClicked((event) -> {
            PrimaryStage.setResizable(false);
            this.setGameBackground(this.scene.getHeight(), this.scene.getWidth());
        });
        this.root.prefWidthProperty().bind(this.root.heightProperty().divide(16).multiply(9));
    }

    private void setGameBackground(final double height, final double width) {
        this.root = new AnchorPane();
        this.scene = new Scene(this.root, width, height);
        final ImageView background = new ImageView(new Image(ClassLoader.getSystemResource("images/sfondo_FINALE.png").toString()));
        background.setPreserveRatio(false);
        background.setSmooth(true);
        background.setVisible(true);
        background.fitWidthProperty().bind(root.widthProperty());
        background.fitHeightProperty().bind(root.heightProperty());
        this.root.getChildren().add(background);
        this.score.setText("Score: 0");
        this.score.setFont(new Font("Arial", this.root.getHeight() / FONT_SIZE));
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
    public void start() {
        this.PrimaryStage.setScene(scene);
        this.PrimaryStage.show();
        System.out.println(root.getHeight() + "    " + root.getWidth());
        final ChangeListener<Number> sceneSizeListener = (observable, oldValue, newValue) -> {
            if (observable.toString().contains("height")) {
                this.PrimaryStage.setWidth(newValue.doubleValue() * ASPECT_RATIO);
                this.PrimaryStage.setMinWidth(PrimaryStage.getWidth());
                this.PrimaryStage.setMaxWidth(PrimaryStage.getWidth());
            }
        };
        if (System.getProperty("os.name").toLowerCase().contains("win") || System.getProperty("os.name").toLowerCase().contains("mac")) {
            this.scene.heightProperty().addListener(sceneSizeListener);
        } else {
            this.PrimaryStage.setMaxWidth(PrimaryStage.getWidth());
            this.PrimaryStage.setMinWidth(MINIMUM_SUPPORTED_HEIGHT_RES * ASPECT_RATIO);
        }
    }

    @Override
    public void setObserver(final SpongebobGameViewObserver observer) {
        this.observer = observer;
    }

}
