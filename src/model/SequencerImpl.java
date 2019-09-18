package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * This class computes the sequence of directions associated with input data points.
 */
public class SequencerImpl {

    private final List<Integer> pointsDirections = new ArrayList<Integer>();
    private final Point[] points;

    /**
     * 
     * @param inputPoints input points to be processed
     */
    public SequencerImpl(final List<Point> inputPoints) {
        int i = 0;
        this.points = new Point[inputPoints.size()];
        for (final Point p : inputPoints) {
            points[i] = p;
            i++;
        }
    }

    /**
     * Computes directions sequence.
     * Note: 45 is not a magic number! it is the angular ratio needed to devide a 360 degree angle in 8 directions.
     * @return array of directions computed from points
     */
    public Integer[] computeSequence() {
        Point lastP = null;
        int cost = 0;
        for (int p = 1; p < points.length; p++) {
            int dir = -1;
            double thetaDeg = computeAngle(points[p - 1], points[p]);
            if ((thetaDeg < 360. && thetaDeg > 360. - 45. / 2.) || (thetaDeg >= 0. && thetaDeg <  45. / 2.)) {
                dir = 0;
            }
            for (int a = 1; a < 8; a++) {
                if (((45. * a - 45. / 2.)) <= thetaDeg && thetaDeg  < ((45. * a + 45. / 2.))) {
                    dir = a;
                }
            }
            if (dir == -1) {
                throw new IllegalArgumentException("Direction not recognized!");
            }
            if (p == 1) {
                pointsDirections.add(dir);
                lastP = points[p];
                continue;
            }
            if (dir == pointsDirections.get(pointsDirections.size() - 1)) {
                continue;
            }
            thetaDeg = computeAngle(lastP, points[p]);
            final int prevDir = pointsDirections.get(pointsDirections.size() - 1);
            if ((thetaDeg < 360. && thetaDeg > 360. - 45. / 2.) || (thetaDeg >= 0. && thetaDeg <  45. / 2.)) {
                continue;
            } else if ((45. * prevDir - 45. / 2.) <= thetaDeg && thetaDeg < (45. * prevDir + 45. / 2.)) {
                continue;
            } else {
                String currString = pointsDirections.toString();
                currString = currString.replace("[", "");
                currString = currString.replace("]", "");
                currString = currString.replace(", ", "");
                final String nextString = currString.concat(Integer.toString(dir));
                final int gain = ModelUtils.computeLevenshteinDistance(currString, nextString);
                cost += distMod8(prevDir, dir);
                if (cost > gain) {
                    cost = 0;
                    pointsDirections.add(dir);
                    lastP = points[p];
                }
            }
        }
        return listToArray(pointsDirections);
    }

    private Integer[] listToArray(final List<Integer> directions) {
        Integer[] intArray = new Integer[directions.size()]; 
        int i = 0;
        for (final Integer d : directions) {
            intArray[i] = d;
            i++;
        }
        return intArray;
    }

    private int distMod8(final int a, final int b) {
        final int diff = Math.abs(b - a);
        return (diff < 4) ? diff : 8 - diff;
    }
    
    private double computeAngle(final Point prec, final Point succ) {
        final int deltaX = prec.x - succ.x;
        final int deltaY = prec.y - succ.y;
        double thetaDeg = Math.toDegrees(Math.atan2(deltaY, deltaX));
        if (thetaDeg < 0) {
            thetaDeg =   180 + (180 + thetaDeg);
        }
        return thetaDeg;
    }

}
