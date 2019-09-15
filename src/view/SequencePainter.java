package view;

import controller.ComparatorThread;
import controller.SpongebobGameController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/** this class is in charged of listening the mouse_drag event and drawing red "dots" on it.
 */
public class SequencePainter implements EventHandler<MouseEvent> {
    
    private static final int STROKE_WIDTH = 5;
    private ComparatorThread comparator;
    private final SpongebobGameController controller;
    
    /**
     * This is the constructor method.
     * @param controller 
     *          SpongebobGameController controller
     */
    public SequencePainter(final SpongebobGameController controller) {
        this.controller = controller;
    }

    @Override
    public final void handle(final MouseEvent arg) {
        comparator = new ComparatorThread(this.controller);
        final Path path = new Path();
        path.setStrokeWidth(STROKE_WIDTH);
        path.setStroke(Color.RED);
        final MoveTo moveTo = new MoveTo(arg.getX(), arg.getY());
        path.getElements().add(moveTo);
        SequencePainter.this.controller.addNode(path);
        final DrawGesture listener = new DrawGesture(path);
        final GestureEndHandler handler = new GestureEndHandler(path, listener);
        this.controller.getRoot().addEventFilter(MouseEvent.MOUSE_RELEASED, handler);
        this.controller.getRoot().addEventFilter(MouseEvent.MOUSE_EXITED, handler);
        this.controller.getRoot().addEventFilter(MouseEvent.MOUSE_DRAGGED, listener);
    }
    
    private class DrawGesture implements EventHandler<MouseEvent> {
        
        private final Path path;

        DrawGesture(final Path path) {
            this.path = path;
        }

        @Override
        public void handle(final MouseEvent e) {
            if (e.getX() > 0 && e.getX() < SequencePainter.this.controller.getRoot().getWidth() && e.getY() > 0 && e.getY() < SequencePainter.this.controller.getRoot().getHeight()) {
                final LineTo line = new LineTo(e.getX(), e.getY());
                SequencePainter.this.controller.removeNode(path);
                path.getElements().add(line);
                SequencePainter.this.comparator.add((int) e.getX(), (int) e.getY());
                SequencePainter.this.controller.addNode(path);
            }
        }
    }
    
    private class GestureEndHandler implements EventHandler<MouseEvent> {
        
        private final Path path;
        private final DrawGesture listener;

        GestureEndHandler(final Path path, final DrawGesture listener) {
            this.path = path;
            this.listener = listener;
        }

        @Override
        public void handle(final MouseEvent e) {
            if (SequencePainter.this.comparator.getState() != Thread.State.RUNNABLE 
                    && SequencePainter.this.comparator.getState() != Thread.State.TERMINATED) {
                SequencePainter.this.controller.getRoot().removeEventFilter(MouseEvent.MOUSE_DRAGGED, listener);
                SequencePainter.this.comparator.start();
            }
            SequencePainter.this.controller.removeNode(path);
            path.getElements().clear();
            SequencePainter.this.controller.getRoot().removeEventFilter(MouseEvent.MOUSE_RELEASED, this);
            SequencePainter.this.controller.getRoot().removeEventFilter(MouseEvent.MOUSE_EXITED, this);
        }
    }
}
