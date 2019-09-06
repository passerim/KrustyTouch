package testprogetto;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;

public class ComparatorThread extends Thread {

    private final int x, y;
    private final int[][] matrix; // m for the y , n for the x ,, m by n matrix
    private final ArrayList<Point> points = new ArrayList<Point>();
    private OptionalDouble value = OptionalDouble.empty();
    //private final Point center;


    public ComparatorThread(final int height, final int width) {
        this.x = width;
        this.y = height;
        //this.center = new Point(x/2, y/2);
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

        for (RefModels model: RefModels.values()) {
            System.out.println(model.getName() + ": " + DTWDistance(pointSequence, model.getSeq()));
        }
    }



    /*private static <X> void printOutput(List<X> list, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") 
                + System.getProperty("file.separator") + filename));)
        {
            for (X p : list) {
                //writer.write("" + p.x + "       " + p.y + "\n");
                writer.write(p.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private Integer DTWDistance(Integer[] a, Integer[] b) {
        int n = a.length, m = b.length;
        System.out.println(n + "	" + m);
        Integer[][] DTW = new Integer[n][m];
        for (int i = 1 ; i < n; i++) {
            for (int j = 1 ; j < m; j++) {
                DTW[i][j] = Integer.MAX_VALUE;
            }
        }
        DTW[0][0] = 0;
        for (int i = 1; i < n; i++) {
            for  (int j = 1 ; j < m; j++) {
                //System.out.println(i + "	" + j);
                Integer cost = Math.abs(a[i-1] - b[j-1]);
                DTW[i][j] = cost + Math.min(DTW[i-1][j], Math.min(DTW[i][j-1],DTW[i-1][j-1]));
            }
        }
        return DTW[n-1][m-1];
    }

    /*private double distance(int x2, int x3, int y2, int y3) {
        double d;
        d = Math.sqrt(Math.pow(x2-x3, 2) + Math.pow(y2-y3, 2));
        return d;
    }*/

    public double getValue() {
        if (this.value.isPresent()) {
            return this.value.getAsDouble();
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
