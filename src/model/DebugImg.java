package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DebugImg {
	
	int[][] imgMatrix;
	String imgName;
	int width, height;
	
	public DebugImg(String name, int x, int y, int[][] matrix) {
		this.imgName = name;
		this.height = y;
		this.width = x;
		this.imgMatrix = new int[this.height][this.width];
		for (int i=0; i<y; i++){
			for (int j=0; j<x; j++){
				imgMatrix[i][j] = matrix[i][j];
			}
		}
		
	}
	
	private BufferedImage createImg() {
		BufferedImage image = new BufferedImage( this.width, this.height, BufferedImage.TYPE_INT_RGB);
	    for(int i=0; i<imgMatrix.length; i++) {
	        for(int j=0; j<imgMatrix[i].length; j++) {
	            int a = imgMatrix[i][j];
	            Color newColor = new Color(255*(a),0,0);
	            image.setRGB(j,i,newColor.getRGB());
	        }
	    }
	    return image;
	}
	
	public void debugImg(String name) {
		if (name == "") 
			name = this.imgName;
		java.io.File output = new java.io.File(name + ".jpg");
	    try {
			ImageIO.write(this.createImg(), "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void modifyMatrix(int i, int j, int v) {
		imgMatrix[i][j] = v;
	}
	
	public void changeMatrix(int[][] loadedImgMatrix) {
		for (int i=0; i<this.height; i++){
			for (int j=0; j<this.width; j++){
				this.modifyMatrix(i, j, loadedImgMatrix[i][j]);
			}
		}
	}

}
