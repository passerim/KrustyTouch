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

import javax.imageio.ImageIO;

public class ImgToMatrixLoader {
	
	private File[] files;
	private int width, height;
	private ArrayList<int[][]> matrixList = new ArrayList<int[][]>();
	
	public ImgToMatrixLoader(final int width, final int height) {
		this.width = width;
		this.height = height;
		String pwd = System.getProperty("user.dir");
		File dir = new File(pwd + "/images");
		this.files = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".jpg");
		    }
		});
		BufferedImage loadedImg = null;
	    for (File f : this.files) {
	    	try {
	    	    loadedImg = ImageIO.read(f);
	    	} catch (IOException e) {
	    		System.out.println("loading image failed...");
	    	}
	    	loadedImg = resizeImg(loadedImg, this.width, this.height);
	    	int[][] loadedImgMatrix = new int[loadedImg.getHeight()][loadedImg.getWidth()];
	    	loadedImgMatrix = bufferedImgToMatrix(loadedImg);
	    	matrixList.add(loadedImgMatrix);
	    	//System.out.println(matrixList.get(0)[0][0]);
	    }
	}
	
	private BufferedImage resizeImg(BufferedImage image,int width,int height) {
	    ImageFilter colorfilter = new ReplicateScaleFilter(width,height);
	    Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), colorfilter));
	    BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
	    Graphics bg = bi.getGraphics();
	    bg.drawImage(img, 0, 0, null);
	    bg.dispose();
	    return bi;
	}
	
	private int[][] bufferedImgToMatrix(BufferedImage loadedImg) {
	    int[][] matr = new int[loadedImg.getHeight()][loadedImg.getWidth()];
	    for (int i = 0; i < matr.length; i++) {
	        for (int j = 0; j < matr[0].length; j++) {
	            //matr[i][j]  = 0;
	            if (((loadedImg.getRGB(j, i)>>16)&0xff) > 250) matr[i][j] = 1;
	        }
	    }
	    //System.out.println(matr[0][0]);
	    return matr;
	}
	
	public int[][] getMatrix(int index) {
		return matrixList.get(index);
	}
	
	public File[] getFiles() {
		return this.files;
	}

}
