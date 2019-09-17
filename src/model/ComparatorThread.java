package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 
 */
public class ComparatorThread implements Comparator {

    private static final double FILTER_COEFF = 1.96;
    private final List<Point> points = new ArrayList<Point>();
    private Optional<RefModels> value = Optional.empty();

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
        final Integer[] pointSequence = new SequencerImpl(points).computeSequence();
        
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
    }

    /**
     * 
     */
    @Override
    public Optional<RefModels> getValue() {
        return this.value;
    }

    /**
     * 
     */
    @Override
    public void add(final int toX, final int toY) {
        final Point p = new Point();
        p.x = toX;
        p.y = toY;
        points.add(p);
    }
    
}
