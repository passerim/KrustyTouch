package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import controller.PlanktonManager;
import controller.SpongebobGameController;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;

public class ComparatorThread extends Thread {

    private final int x, y;
    private final int[][] matrix; // m for the y , n for the x ,, m by n matrix
    private final ArrayList<Point> points = new ArrayList<Point>();
    private Optional<RefModels> value = Optional.empty();
    private SpongebobGameController controller;
    private AnchorPane root;

    public ComparatorThread(final int height, final int width, final SpongebobGameController controller, final AnchorPane root) {
        this.controller = controller;
        this.root = root;
        this.x = width;
        this.y = height;
        this.matrix = new int[y][x];
        for (int i=0; i<y; i++){
            for (int j=0; j<x; j++){
                matrix[i][j] = 0;
            }
        }       
    }

    @Override
    public void run() {

        // Printing image for debug
        DebugImg img0 = new DebugImg("RedScale", x, y, matrix);
        img0.debugImg("");

        // Computing average and filtering data points
        double avg = 0;
        for (int i = 0; i<points.size()-1; i++) {
            avg += ModelUtils.distance(points.get(i).x, points.get(i+1).x, points.get(i).y, points.get(i+1).y);
        }
        avg = avg / points.size();
        boolean filtra = true;
        while (filtra) {
            filtra = false;
            for (int i = 0; i<points.size()-2; i++) {
                if (ModelUtils.distance(points.get(i).x, points.get(i+2).x, points.get(i).y, points.get(i+2).y) < 1.96*avg) {
                    img0.modifyMatrix(points.get(i+1).y, points.get(i+1).x, 0);
                    points.remove(i+1);
                    if (ModelUtils.distance(points.get(i).x, points.get(i+1).x, points.get(i).y, points.get(i+1).y) < avg) filtra = true;
                }
            }
        }

        // Printing image for debug
        img0.debugImg("RedScaled");

        // Getting drawn points sequence
        Sequencer pointsSeq = new Sequencer(points);
        final Integer[] pointSequence = pointsSeq.computeSequence();
        System.out.println(Arrays.deepToString(pointSequence));
        List<Double> valList = new ArrayList<>();
        for (RefModels model: RefModels.values()) {
            /*final String modelSeq = Arrays.deepToString(model.getSeq()).chars().mapToObj(i->(char)i)
                    .filter(ch->ch != '[' && ch != ']' && ch != ',' && ch != ' ')
                    .map(cha->String.valueOf(cha))
                    .reduce(String::concat).get();
            final String pointSqnc = Arrays.deepToString(pointSequence).chars().mapToObj(i->(char)i)
                    .filter(ch->ch != '[' && ch != ']' && ch != ',' && ch != ' ')
                    .map(cha->String.valueOf(cha))
                    .reduce(String::concat).get();*/
            //System.out.println(model.getName() + ": " + ModelUtils.computeLevenshteinDistance(pointSqnc, modelSeq));
            //System.out.println(model.getName() + ": " + ModelUtils.DTWDistance(pointSequence, model.getSeq()));
            //System.out.println(model.getName() + ": " + new ModelComparator(pointSequence, model).getResult());
            valList.add((double) new ModelComparator(pointSequence, model).getResult());
        }
        valList = softmax(valList);
        double v = 0;
        int i = 0;
        for (RefModels model: RefModels.values()) {
            if (valList.get(i)>v && valList.get(i)>.5) {
                v = valList.get(i);
                this.value = Optional.of(model);
                if (model.getName() == "circle") {
                    System.out.println(valList.get(i));
                }
            }
            i++;
        }
        if (this.value.isPresent()) {
            System.out.println(this.value.get());
            //System.out.println(this.controller.getModel().getMap());
            if (this.controller.getModel().canRemove(this.value.get())) {
                PlanktonManager p = this.controller.getModel().getMap().get(this.value.get()).get(0);
                p.stop();
                this.controller.getModel().getMap().get(this.value.get()).remove(p);
                Platform.runLater(() -> {
                    this.root.getChildren().remove(p.Plankton);
                    if (!this.controller.getModel().getMap().get(this.value.get()).contains(p)) {
                        System.out.println("removed: " + this.value.get());
                    }
                });
            }
        }
    }

    private List<Double> softmax(List<Double> valList) {
        final List<Double> ret = new ArrayList<Double>();
        Double sum = (double) 0;
        for (int i = 0; i < valList.size(); i++) {
            sum += 1/Math.exp(1*valList.get(i));
        }
        for (int i = 0; i < valList.size(); i++) {
            ret.add((1/Math.exp(1*valList.get(i))/sum));
        }
        return ret;
    }

    public RefModels getValue() {
        if (this.value.isPresent()) {
            return this.value.get();
        } else {
            throw new IllegalStateException("ComparatorThread: " + this.getId() + ", value is not available!");
        }
    }

    public void add(int toX, int toY) {
        matrix[toY][toX] = 1;
        Point p = new Point();
        p.x = toX;
        p.y = toY;
        points.add(p);
    }
}
