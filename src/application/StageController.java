package application;




//import java.math.*;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.image.ImageView;


public class StageController {
	
	@FXML
	
	private ImageView sponge;


    public void MoveSpongebob () {
    	int increments=(int) ((Math.random()*201) -100);
    	if(increments>=0) {
    		sponge.setRotate(0);
        	sponge.setRotationAxis(new Point3D(0, 0, 1));
    		sponge.setLayoutX(sponge.getLayoutX()+increments);
    	}else {
     		sponge.setRotate(180);
        	sponge.setRotationAxis(new Point3D(0, 180, 1));
        	sponge.setLayoutX(sponge.getLayoutX()+increments);
    	}
    }
  

}


