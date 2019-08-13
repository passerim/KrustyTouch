package application;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class PlanktonManager extends Thread {
	@FXML	
	private AnchorPane root;
	public ImageView Plankton= new ImageView(new Image("Images/plankton4.png"));
	private int interchanger=0;
	private boolean exit=false;
	TranslateTransition translate= new TranslateTransition();

	//constructor used in order to obtain the main panel.
	public PlanktonManager(AnchorPane base) {
		this.root=base;

	}

	//main function, after waiting a couple of seconds it randomly spawn a plankton
	public void run() {
		RandomSpawn();
		double transition= (root.getMaxHeight()*60)/100;
		translate.setNode(Plankton);
		translate.setFromY(0);
		translate.setToY(transition);
		translate.setDuration(Duration.millis(5000));
		translate.play();
		translate.setOnFinished((event) -> Platform.runLater(()->root.getChildren().remove(this.Plankton)));


			while(!exit){
				try {
					sleep(250);
					if(interchanger==0){
						Plankton.setImage(new Image("Images/plankton5.png"));
						interchanger=1;
					}else{
						Plankton.setImage(new Image("Images/plankton4.png"));
						interchanger=0;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}



			}

			
		
		
	}


	//a function to set a random location of spawning
	public void RandomSpawn() {
		double lowerBound = (root.getMaxHeight()*10)/100;
		double upperBound = (root.getMaxHeight()*70)/100;
		int location=(int) ((Math.random()*(lowerBound+upperBound)) +lowerBound);
		this.Plankton.setLayoutX(location);
		this.Plankton.setLayoutY(0);
		this.Plankton.setVisible(true);
	}
}
