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
import org.omg.CORBA.TRANSACTION_MODE;

public class PlanktonManager extends Thread {
	@FXML	
	private Pane root;
	public ImageView Plankton= new ImageView(new Image("Images/plankton4.png"));
	private int interchanger=0;
	private boolean exit=false;

	//constructor used in order to obtain the main panel.
	public PlanktonManager(Pane base) {
		this.root=base;

	}

	//main function, after waiting a couple of seconds it randomly spawn a plankton
	public void run() {
		RandomSpawn();
		TranslateTransition translate= new TranslateTransition();
		translate.setNode(Plankton);
		translate.setFromY(0);
		translate.setToY(600);
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
		int location=(int) ((Math.random()*450) +50);
		this.Plankton.setLayoutX(location);
		this.Plankton.setLayoutY(0);
		this.Plankton.setVisible(true);
	}
}
