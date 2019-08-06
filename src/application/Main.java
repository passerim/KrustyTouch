package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {
	@Override
	public void start(Stage PrimaryStage) {
		try {
			Pane root= FXMLLoader.load(getClass().getResource("Scena.fxml"));

			Scene scene = new Scene(root);
			SpongebobManager thread = new SpongebobManager(root);
			thread.start();
			SpawnerPlanktonManager SpawnerManager = new SpawnerPlanktonManager(root);
			SpawnerManager.start();
			PrimaryStage.setHeight(1000);
			PrimaryStage.setWidth(500);
			PrimaryStage.setResizable(false);
			PrimaryStage.setScene(scene);
			PrimaryStage.sizeToScene();
			PrimaryStage.show();

			

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
