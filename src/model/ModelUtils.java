package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ModelUtils {

    private ModelUtils() {
    }

    public static double distance(final int x1, final int x2, final int y1, final int y2) {
        double d;
        d = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
        return d;
    }

    public static <X> void printOutput(final List<X> list, final String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") 
                + System.getProperty("file.separator") + filename));)
        {
            for (X p : list) {
                writer.write(p.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int computeLevenshteinDistance(final CharSequence lhs, final CharSequence rhs) {      
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        

        for (int i = 0; i <= lhs.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= rhs.length(); j++)                                 
            distance[0][j] = j;                                                  

        for (int i = 1; i <= lhs.length(); i++)                                 
            for (int j = 1; j <= rhs.length(); j++)                             
                distance[i][j] = Math.min(                                        
                        distance[i - 1][j] + 1, Math.min(                                
                                distance[i][j - 1] + 1,                                  
                                distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1)));

        return distance[lhs.length()][rhs.length()];                           
    }

    public static Integer DTWDistance(final Integer[] a, final Integer[] b) {
        int n = a.length, m = b.length;
        Integer[][] DTW = new Integer[n+1][m+1];
        for (int i = 0 ; i <= n; i++) {
            for (int j = 0 ; j <= m; j++) {
                DTW[i][j] = Integer.MAX_VALUE;
            }
        }
        DTW[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for  (int j = 1 ; j <= m; j++) {
                Integer cost = distMod8(a[i-1] , b[j-1]);
                DTW[i][j] = cost + Math.min(DTW[i-1][j], Math.min(DTW[i][j-1],DTW[i-1][j-1]));
            }
        }
        return DTW[n][m];
    }

    public static int distMod8(final int a, final int b)
    {
        int diff = Math.abs( b - a );
        return ( diff < 4 ) ? diff : 8 - diff;
    }

    public static Integer invertMod8(final Integer n) {
        return n >= 4 ? n - 4 : n + 4;
    }

    public static List<Double> softmax(final List<Double> valList) {
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

}
