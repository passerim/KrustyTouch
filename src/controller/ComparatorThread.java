package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import model.ModelComparatorImpl;
import model.ModelUtils;
import model.RefModels;
import model.SequencerImpl;

/**
 * 
 */
public class ComparatorThread extends Thread {

    private static final double FILTER_COEFF = 1.96;
    private final List<Point> points = new ArrayList<Point>();
    private Optional<RefModels> value = Optional.empty();
    private final SpongebobGameController controller;

    /**
     * 
     * @param controller
     *          reference to controller
     */
    public ComparatorThread(final SpongebobGameController controller) {
        super();
        this.controller = controller;     
    }

    /**
     * 
     */
    @Override
    public void run() {

        // Computing average and filtering data points
        double avg = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            avg += ModelUtils.distance(points.get(i).x, points.get(i + 1).x, points.get(i).y, points.get(i + 1).y);
        }
        avg = avg / points.size();
        boolean filtra = true;
        while (filtra) {
            filtra = false;
            for (int i = 0; i < points.size() - 2; i++) {
                if (ModelUtils.distance(points.get(i).x, points.get(i + 2).x, points.get(i).y, points.get(i + 2).y) < FILTER_COEFF * avg) {
                    points.remove(i + 1);
                    if (ModelUtils.distance(points.get(i).x, points.get(i + 1).x, points.get(i).y, points.get(i + 1).y) < avg) {
                        filtra = true;
                    }
                }
            }
        }

        // Getting drawn points sequence
        final SequencerImpl pointsSeq = new SequencerImpl(points);
        final Integer[] pointSequence = pointsSeq.computeSequence();
        
        // Comparing reference models to drawn points directions sequence
        List<Double> valList = new ArrayList<>();
        for (final RefModels model: RefModels.values()) {
            valList.add((double) new ModelComparatorImpl(pointSequence, model).getResult());
        }
        
        // Getting highest probability reference model
        valList = ModelUtils.softmax(valList);
        double v = 0;
        int i = 0;
        for (final RefModels model: RefModels.values()) {
            if (valList.get(i) > v && valList.get(i) > .5) {
                v = valList.get(i);
                this.value = Optional.of(model);
            }
            i++;
        }
        
        // Removing reference model
        if (this.value.isPresent()) {
            if (this.controller.getModel().canRemove(this.value.get())) {
                final PlanktonManager p = this.controller.getModel().getMap().get(this.value.get()).get(0);
                p.disable();
                p.stopTransition();
                this.controller.getModel().getMap().get(this.value.get()).remove(p);
                Platform.runLater(() -> {
                    this.controller.updateScore();
                    this.controller.removeNode(p.plankton);
                });
            }
        }
    }

    /**
     * Get recognized symbol if any.
     * @return
     *          recognized symbol
     */
    public RefModels getValue() {
        if (this.value.isPresent()) {
            return this.value.get();
        } else {
            throw new IllegalStateException("ComparatorThread: " + this.getId() + ", value is not available!");
        }
    }

    /**
     * Adds a point to be processed.
     * @param toX
     *          x coordinate of point
     * @param toY
     *          y coordinate of point
     */
    public void add(final int toX, final int toY) {
        final Point p = new Point();
        p.x = toX;
        p.y = toY;
        points.add(p);
    }
    
    
}
