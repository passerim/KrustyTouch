package testprogetto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class ModelUtils {

    public static final ModelUtils UTILS = new ModelUtils();

    public ModelUtils() {
    }

    public static ModelUtils getUtils() {
        return UTILS;
    }

    public static double distance(int x1, int x2, int y1, int y2) {
        double d;
        d = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
        return d;
    }

    private static <X> void printOutput(List<X> list, String filename) {
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
    }



}
