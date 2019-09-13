package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ReplicateScaleFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImgToMatrixLoader {

    private final File[] files;
    private final int width, height;
    private final List<int[][]> matrixList = new ArrayList<int[][]>();

    public ImgToMatrixLoader(final int width, final int height) {
        this.width = width;
        this.height = height;
        final String pwd = System.getProperty("user.dir");
        final File dir = new File(pwd + "/images");
        this.files = dir.listFiles(new FilenameFilter() {
            public boolean accept(final File dir, final String name) {
                return name.toLowerCase().endsWith(".jpg");
            }
        });
        BufferedImage loadedImg = null;
        for (final File f : this.files) {
            try {
                loadedImg = ImageIO.read(f);
            } catch (IOException e) {
                System.out.println("loading image failed...");
            }
            loadedImg = resizeImg(loadedImg, this.width, this.height);
            int[][] loadedImgMatrix = new int[loadedImg.getHeight()][loadedImg.getWidth()];
            loadedImgMatrix = bufferedImgToMatrix(loadedImg);
            matrixList.add(loadedImgMatrix);
        }
    }

    private BufferedImage resizeImg(final BufferedImage image, final int width, final int height) {
        final ImageFilter colorfilter = new ReplicateScaleFilter(width, height);
        final Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), colorfilter));
        final BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        final Graphics bg = bi.getGraphics();
        bg.drawImage(img, 0, 0, null);
        bg.dispose();
        return bi;
    }

    private int[][] bufferedImgToMatrix(final BufferedImage loadedImg) {
        final int[][] matr = new int[loadedImg.getHeight()][loadedImg.getWidth()];
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr[0].length; j++) {
                if (((loadedImg.getRGB(j, i)>>16)&0xff) > 250) matr[i][j] = 1;
            }
        }
        return matr;
    }

    public int[][] getMatrix(final int index) {
        return matrixList.get(index);
    }

    public File[] getFiles() {
        return this.files.clone();
    }

}
