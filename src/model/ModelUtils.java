package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class implementing multiple useful functions used in model.
 */
public final class ModelUtils {

    private ModelUtils() {
    }

    /**
     * Computes distance between two points.
     * @param x1
     *          x of first point
     * @param x2
     *          x of second point
     * @param y1
     *          y of first point
     * @param y2
     *          y of second point
     * @return
     *          distance between two points
     */
    public static double distance(final int x1, final int x2, final int y1, final int y2) {
        double d;
        d = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        return d;
    }

    /**
     * Prints on text file a list of objects of type X.
     * @param <X>
     *          type of objects to be printed
     * @param list
     *          list of objects to be printed of type X
     * @param filename
     *          output filename
     */
    public static <X> void printOutput(final List<X> list, final String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") 
                + System.getProperty("file.separator") + filename));) {
            for (final X p : list) {
                writer.write(p.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Computes Levenshtein distance between two sequences of characters.
     * @param lhs
     *          first sequence
     * @param rhs
     *          second sequence
     * @return
     *          Levenshtein cost
     */
    public static int computeLevenshteinDistance(final CharSequence lhs, final CharSequence rhs) {      
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        

        for (int i = 0; i <= lhs.length(); i++) {
            distance[i][0] = i;
        }                                                  
        for (int j = 1; j <= rhs.length(); j++) {
            distance[0][j] = j;
        }                                                  
        for (int i = 1; i <= lhs.length(); i++) {
            for (int j = 1; j <= rhs.length(); j++) {                          
                distance[i][j] = Math.min(distance[i - 1][j] + 1, Math.min(distance[i][j - 1] + 1,                                  
                                distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1)));
            }
        }
        return distance[lhs.length()][rhs.length()];                           
    }

    /**
     * Computes dynamic time warping distance betwen two arrays of integers.
     * @param a
     *          first array
     * @param b
     *          second array
     * @return
     *          dynamic time warping cost
     */
    public static Integer dtwDistance(final Integer[] a, final Integer[] b) {
        final int n = a.length, m = b.length;
        Integer[][] dtw = new Integer[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dtw[i][j] = Integer.MAX_VALUE;
            }
        }
        dtw[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for  (int j = 1; j <= m; j++) {
                final Integer cost = distMod8(a[i - 1], b[j - 1]);
                dtw[i][j] = cost + Math.min(dtw[i - 1][j], Math.min(dtw[i][j - 1], dtw[i - 1][j - 1]));
            }
        }
        return dtw[n][m];
    }

    /**
     * Computes distance between two directions in modular arithmetic.
     * @param a
     *          first value
     * @param b
     *          second value
     * @return
     *          distance
     */
    public static int distMod8(final int a, final int b) {
        final int diff = Math.abs(b - a);
        return (diff < 4) ? diff : 8 - diff;
    }

    /**
     * Computes value of maximum distance from input value in clock's arithmetic.
     * @param n
     *          value
     * @return
     *          opposite value in clock's arithmetic
     */
    public static Integer invertMod8(final Integer n) {
        return n >= 4 ? n - 4 : n + 4;
    }

    /**
     * Applies softmax function to input list of doubles.
     * @param valList
     *          list to be processed
     * @return
     *          transformed list
     */
    public static List<Double> softmax(final List<Double> valList) {
        final List<Double> ret = new ArrayList<Double>();
        Double sum = (double) 0;
        for (int i = 0; i < valList.size(); i++) {
            sum += 1 / Math.exp(1 * valList.get(i));
        }
        for (int i = 0; i < valList.size(); i++) {
            ret.add((1 / Math.exp(1 * valList.get(i)) / sum));
        }
        return ret;
    }

}
