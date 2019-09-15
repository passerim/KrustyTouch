package controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import model.ModelComparatorImpl;
import model.ModelUtils;
import model.RefModels;
import model.SequencerImpl;

public class ComparatorThread extends Thread {

    private final ArrayList<Point> points = new ArrayList<Point>();
    private Optional<RefModels> value = Optional.empty();
    private final SpongebobGameController controller;
    private final AnchorPane root;

    public ComparatorThread(final SpongebobGameController controller, final AnchorPane root) {
        this.controller = controller;
        this.root = root;       
    }

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
                if (ModelUtils.distance(points.get(i).x, points.get(i + 2).x, points.get(i).y, points.get(i + 2).y) < 1.96 * avg) {
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
        //System.out.println(Arrays.deepToString(pointSequence));
        List<Double> valList = new ArrayList<>();
        //valList = Arrays.asList(RefModels.values()).stream().map(m->(double) new ModelComparator(pointSequence, m).getResult()).collect(Collectors.toList());
        for (final RefModels model: RefModels.values()) {
            valList.add((double) new ModelComparatorImpl(pointSequence, model).getResult());
        }
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
        if (this.value.isPresent()) {
            System.out.println(this.value.get());
            //System.out.println(this.controller.getModel().getMap());
            if (this.controller.getModel().canRemove(this.value.get())) {
                final PlanktonManager p = this.controller.getModel().getMap().get(this.value.get()).get(0);
                p.disable();
                p.stopTransition();
                this.controller.getModel().getMap().get(this.value.get()).remove(p);
                Platform.runLater(() -> {
                    this.controller.updateScore();
                    this.root.getChildren().remove(p.Plankton);
                    if (!this.controller.getModel().getMap().get(this.value.get()).contains(p)) {
                        System.out.println("removed: " + this.value.get());
                    }
                });
            }
        }
    }

    public RefModels getValue() {
        if (this.value.isPresent()) {
            return this.value.get();
        } else {
            throw new IllegalStateException("ComparatorThread: " + this.getId() + ", value is not available!");
        }
    }

    public void add(final int toX, final int toY) {
        final Point p = new Point();
        p.x = toX;
        p.y = toY;
        points.add(p);
    }
}
