package application;




 

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class SpongebobManager extends Thread {
	@FXML
	
	ImageView Spongebob=new ImageView();
	Pane root;
	private int walking_position=0;
	TranslateTransition movements = new TranslateTransition();
	public SpongebobManager (Pane base) {
		this.root = base;
	}
	public void run() {
		Spongebob.setImage(new Image("Images/walking_spongebob_1.png"));
		Spongebob.setLayoutX(0);
		Spongebob.setLayoutY(695);
		Spongebob.setVisible(true);
		this.root.getChildren().add(Spongebob);

		movements.setNode(Spongebob);
		move_to_the_left();



			while(true) {
				try {
					sleep(250);
					if (walking_position == 0) {
						Spongebob.setImage(new Image("Images/walking_spongebob_2.png"));
						walking_position = 1;
					} else {
						Spongebob.setImage(new Image("Images/walking_spongebob_1.png"));
						walking_position = 0;
					}



				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
	}
	private void move_to_the_left(){
		Spongebob.setRotate(0);
		Spongebob.setRotationAxis(new Point3D(0, 0, 1));
		movements.setFromX(0);
		movements.setToX(300);
		movements.setDuration(Duration.millis(10000));
		movements.setOnFinished((event)->move_to_the_right());
		movements.play();
			}
	private void move_to_the_right(){
		Spongebob.setRotate(180);
		Spongebob.setRotationAxis(new Point3D(0, 180, 1));
		movements.setFromX(300);
		movements.setToX(0);
		movements.setDuration(Duration.millis(10000));
		movements.setOnFinished((event)->move_to_the_left());
		movements.play();
	}
	private void MovementAlgorithm() {
		int increments=(int) ((Math.random()*201) -100);
    	if(increments>=0) {

    		Spongebob.setLayoutX(Spongebob.getLayoutX()+increments);
    	}else {

        	Spongebob.setLayoutX(Spongebob.getLayoutX()+increments);
    	}
	}
	}


