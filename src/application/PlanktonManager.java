package application;


import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.util.Duration;

import java.util.concurrent.TimeUnit;


public class PlanktonManager extends Thread {
	@FXML	
	private AnchorPane root;
	public ImageView Plankton= new ImageView(new Image("Images/plankton4.png"));
	private int interchanger=0;
	TranslateTransition transition = new TranslateTransition();


	//constructor used in order to obtain the main panel.
	public PlanktonManager(AnchorPane base) {
		this.root=base;

	}

	//main function, after waiting a couple of seconds it randomly spawn a plankton
	public void run() {
		RandomSpawn();
		Plankton.setFitHeight(71);
		Plankton.setFitWidth(81);
		SetTransition();


			while(true){
				try {
					TimeUnit.MILLISECONDS.sleep(250); // mantengo  questo per il momento
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
	//a function created in order to move plankton on the screen
	public void SetTransition(){
		transition.setNode(this.Plankton);
		transition.setFromY(0);
		transition.setToY((root.getMaxHeight()*60)/100);
		transition.setDuration(Duration.millis(5000));
		transition.play();
		transition.setOnFinished((event) -> Platform.runLater(()->root.getChildren().remove(this.Plankton)));
		transition.setOnFinished((event)->Platform.runLater(()->root.getChildren().removeAll(this.Plankton)));
	}
	public void Stermination(){
		Platform.runLater(()-> this.root.getChildren().removeAll(this.Plankton));
	}
}
