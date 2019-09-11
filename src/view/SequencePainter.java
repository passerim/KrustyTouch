package view;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import model.ComparatorThread;

public class SequencePainter implements EventHandler<MouseEvent> {
    
    private AnchorPane root;
    private ComparatorThread comparator;

    public SequencePainter(final AnchorPane root) {
        this.root = root;
    }

    @Override
    public void handle(MouseEvent arg) {
        comparator = new ComparatorThread((int) root.getHeight(), (int) root.getWidth());
        Path path = new Path();
        path.setStrokeWidth(5);
        path.setStroke(Color.RED);
        MoveTo moveTo = new MoveTo(arg.getX(), arg.getY());
        path.getElements().add(moveTo);
        SequencePainter.this.root.getChildren().add(path);
        DrawGesture listener = new DrawGesture(path);
        GestureEndHandler handler = new GestureEndHandler(path, listener);
        this.root.addEventFilter(MouseEvent.MOUSE_RELEASED, handler);
        this.root.addEventFilter(MouseEvent.MOUSE_EXITED, handler);
        this.root.addEventFilter(MouseEvent.MOUSE_DRAGGED, listener);
    }
    
    private class DrawGesture implements EventHandler<MouseEvent> {
        
        private Path path;

        public DrawGesture(final Path path) {
            this.path = path;
        }

        @Override
        public void handle(MouseEvent e) {
            if (e.getX() > 0 && e.getX() < root.getWidth() && e.getY() > 0 && e.getY() < root.getHeight()) {
                LineTo line = new LineTo(e.getX(), e.getY());
                SequencePainter.this.root.getChildren().remove(path);
                path.getElements().add(line);
                SequencePainter.this.comparator.add((int) e.getX(), (int) e.getY());
                SequencePainter.this.root.getChildren().add(path);
            }
        }
    }
    
    private class GestureEndHandler implements EventHandler<MouseEvent> {
        
        private Path path;
        private DrawGesture listener;

        public GestureEndHandler(final Path path, DrawGesture listener) {
            this.path = path;
            this.listener = listener;
        }

        @Override
        public void handle(MouseEvent e) {
            if (SequencePainter.this.comparator.getState()!=Thread.State.RUNNABLE 
                    && SequencePainter.this.comparator.getState()!=Thread.State.TERMINATED) {
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
