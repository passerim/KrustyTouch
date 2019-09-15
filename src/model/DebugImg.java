package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Test class for debugging purpose only.
 */
public class DebugImg {

    private final int[][] imgMatrix;
    private final String imgName;
    private final int width, height;

    /**
     * 
     * @param name
     *          image name
     * @param x
     *          image width
     * @param y
     *          image height
     * @param matrix
     *          matrix
     */
    public DebugImg(final String name, final int x, final int y, final int[][] matrix) {
        this.imgName = name;
        this.height = y;
        this.width = x;
        this.imgMatrix = new int[this.height][this.width];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                imgMatrix[i][j] = matrix[i][j];
            }
        }
    }

    private BufferedImage createImg() {
        final BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < imgMatrix.length; i++) {
            for (int j = 0; j < imgMatrix[i].length; j++) {
                final int a = imgMatrix[i][j];
                final Color newColor = new Color(255 * (a), 0, 0);
                image.setRGB(j, i, newColor.getRGB());
            }
        }
        return image;
    }

    /**
     * Prints on file current matrix as image.
     * @param name
     *          debug image name
     */
    public void debugImg(String name) {
        if (name.equals("")) {
            name = this.imgName;
        }
        final File output = new File(name + ".jpg");
        try {
            ImageIO.write(this.createImg(), "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter for a value of the matrix to be printed.
     * @param i
     *          rows index
     * @param j
     *          columns index
     * @param v
     *          value
     */
    public void modifyMatrix(final int i, final int j, final int v) {
        this.imgMatrix[i][j] = v;
    }

    /**
     * Overwrites current matrix with specified one.
     * @param loadedImgMatrix
     *          new matrix
     */
    public void changeMatrix(final int[][] loadedImgMatrix) {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.modifyMatrix(i, j, loadedImgMatrix[i][j]);
            }
        }
    }

}
