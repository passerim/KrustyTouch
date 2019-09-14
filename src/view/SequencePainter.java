package view;

import controller.ComparatorThread;
import controller.SpongebobGameController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class SequencePainter implements EventHandler<MouseEvent> {
    
    private static final int STROKE_WIDTH = 5;
    private final AnchorPane root;
    private ComparatorThread comparator;
    private final SpongebobGameController controller;

    public SequencePainter(final AnchorPane root, final SpongebobGameController controller) {
        this.root = root;
        this.controller = controller;
    }

    @Override
    public void handle(final MouseEvent arg) {
        comparator = new ComparatorThread(this.controller, this.root);
        final Path path = new Path();
        path.setStrokeWidth(STROKE_WIDTH);
        path.setStroke(Color.RED);
        final MoveTo moveTo = new MoveTo(arg.getX(), arg.getY());
        path.getElements().add(moveTo);
        SequencePainter.this.root.getChildren().add(path);
        final DrawGesture listener = new DrawGesture(path);
        final GestureEndHandler handler = new GestureEndHandler(path, listener);
        this.root.addEventFilter(MouseEvent.MOUSE_RELEASED, handler);
        this.root.addEventFilter(MouseEvent.MOUSE_EXITED, handler);
        this.root.addEventFilter(MouseEvent.MOUSE_DRAGGED, listener);
    }
    
    private class DrawGesture implements EventHandler<MouseEvent> {
        
        private final Path path;

        public DrawGesture(final Path path) {
            this.path = path;
        }

        @Override
        public void handle(final MouseEvent e) {
            if (e.getX() > 0 && e.getX() < root.getWidth() && e.getY() > 0 && e.getY() < root.getHeight()) {
                final LineTo line = new LineTo(e.getX(), e.getY());
                SequencePainter.this.root.getChildren().remove(path);
                path.getElements().add(line);
                SequencePainter.this.comparator.add((int) e.getX(), (int) e.getY());
                SequencePainter.this.root.getChildren().add(path);
            }
        }
    }
    
    private class GestureEndHandler implements EventHandler<MouseEvent> {
        
        private final Path path;
        private final DrawGesture listener;

        public GestureEndHandler(final Path path, final DrawGesture listener) {
            this.path = path;
            this.listener = listener;
        }

        @Override
        public void handle(final MouseEvent e) {
            if (SequencePainter.this.comparator.getState() != Thread.State.RUNNABLE 
                    && SequencePainter.this.comparator.getState() != Thread.State.TERMINATED) {
                SequencePainter.this.root.removeEventFilter(MouseEvent.MOUSE_DRAGGED, listener);
                SequencePainter.this.comparator.start();
            }
            SequencePainter.this.root.getChildren().remove(path);
            path.getElements().clear();
            SequencePainter.this.root.removeEventFilter(MouseEvent.MOUSE_RELEASED, this);
            SequencePainter.this.root.removeEventFilter(MouseEvent.MOUSE_EXITED, this);
        }
    }
}
