package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.Pair;

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
    private final Stage primaryStage;
    private Scene scene;
    private final Label score = new Label();
    private static final int STROKE_WIDTH = 5;
    private List<Pair<Integer, Integer>> points;
    private Path path;
    private DrawGesture listener;

    /** this is the constructor method, which initiates the menu, first, and the game background right after.
     * 
     * @param primaryStage primaryStage is the window
     * @param observer SpongebobGameViewObserver 
     */
    public SpongebobGameViewImpl(final Stage primaryStage, final SpongebobGameViewObserver observer) {
        this.primaryStage = primaryStage;
        this.observer = observer;
        this.root = new AnchorPane();
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.primaryStage.setMaxHeight(screenSize.getHeight() * DEFAULT_HEIGHT_TO_SCREEN_RATIO);
        this.primaryStage.setMaxWidth(this.primaryStage.getMaxHeight() * ASPECT_RATIO);
        this.primaryStage.setTitle(FRAME_NAME);
        this.primaryStage.centerOnScreen();
        this.primaryStage.setMaximized(false);
        this.primaryStage.setFullScreen(false);
        this.primaryStage.setOnCloseRequest(we -> this.observer.quit());
        this.primaryStage.setResizable(true);
        this.primaryStage.setMinHeight(MINIMUM_SUPPORTED_HEIGHT_RES);
        this.setMenuBackground(primaryStage, screenSize);
    }

    private void setMenuBackground(final Stage primaryStage, final Dimension screenSize) {
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
            primaryStage.setResizable(false);
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
        this.root.addEventFilter(MouseEvent.DRAG_DETECTED, new SequencePainter());
        this.observer.newGame();
        primaryStage.setScene(this.scene);
    }

    @Override
    public void setScore(final int val) {
        this.score.setText("Score: " + val);
    }

    @Override
    public void start() {
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        System.out.println(root.getHeight() + "    " + root.getWidth());
        final ChangeListener<Number> sceneSizeListener = (observable, oldValue, newValue) -> {
            if (observable.toString().contains("height")) {
                this.primaryStage.setWidth(newValue.doubleValue() * ASPECT_RATIO);
                this.primaryStage.setMinWidth(primaryStage.getWidth());
                this.primaryStage.setMaxWidth(primaryStage.getWidth());
            }
        };
        // Attivare il listener, che mantiene la finestra con aspect ratio 16:9 genera bug su Linux
        // si è quindi scelto di lasciarlo disattivato.
        //this.scene.heightProperty().addListener(sceneSizeListener);
        this.primaryStage.setMaxWidth(primaryStage.getWidth());
        this.primaryStage.setMinWidth(MINIMUM_SUPPORTED_HEIGHT_RES * ASPECT_RATIO);
    }

    @Override
    public void setObserver(final SpongebobGameViewObserver observer) {
        this.observer = observer;
    }

    @Override
    public Pane getRoot() {
        return this.root;
    }

    @Override
    public void addChildren(final Node e) {
        this.root.getChildren().add(e);
    }

    @Override
    public void removeChildren(final Node e) {
        this.root.getChildren().remove(e);
    }

    private void dragDetected(final MouseEvent arg) {
        this.points = new LinkedList<>();
        this.points.add(new Pair<>((int) arg.getX(), (int) arg.getY()));
        this.path = new Path();
        this.path.setStrokeWidth(STROKE_WIDTH);
        this.path.setStroke(Color.RED);
        final MoveTo moveTo = new MoveTo(arg.getX(), arg.getY());
        this.path.getElements().add(moveTo);
        this.root.getChildren().add(this.path);
        this.listener = new DrawGesture();
        final GestureEndHandler handler = new GestureEndHandler();
        this.root.addEventFilter(MouseEvent.MOUSE_RELEASED, handler);
        this.root.addEventFilter(MouseEvent.MOUSE_EXITED, handler);
        this.root.addEventFilter(MouseEvent.MOUSE_DRAGGED, this.listener);
    }

    private void dragged(final MouseEvent e) {
        if (e.getX() > 0 && e.getX() < this.root.getWidth() && e.getY() > 0 && e.getY() < this.root.getHeight()) {
            final LineTo line = new LineTo(e.getX(), e.getY());
            this.root.getChildren().remove(this.path);
            this.path.getElements().add(line);
            this.points.add(new Pair<Integer, Integer>((int) e.getX(), (int) e.getY()));
            this.root.getChildren().add(this.path);
        }
    }

    private void endDrag(final EventHandler<MouseEvent> myself) {
        this.root.removeEventFilter(MouseEvent.MOUSE_DRAGGED, this.listener);
        this.observer.compare(this.points);
        this.root.getChildren().remove(this.path);
        this.path.getElements().clear();
        this.root.removeEventFilter(MouseEvent.MOUSE_RELEASED, myself);
        this.root.removeEventFilter(MouseEvent.MOUSE_EXITED, myself);
    }

    private class SequencePainter implements EventHandler<MouseEvent> {

        @Override
        public final void handle(final MouseEvent arg) {
            SpongebobGameViewImpl.this.dragDetected(arg);
        }
    }

    private class DrawGesture implements EventHandler<MouseEvent> {

        @Override
        public void handle(final MouseEvent e) {
            SpongebobGameViewImpl.this.dragged(e);
        }
    }

    private class GestureEndHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(final MouseEvent e) {
            SpongebobGameViewImpl.this.endDrag(this);
        }
    }

}
