package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {
	@Override
	public void start(Stage PrimaryStage) {
		try {
			Pane root= FXMLLoader.load(getClass().getResource("Scena.fxml"));




			Scene scene = new Scene(root);
			root.getStylesheets().addAll(this.getClass().getResource("Application.css").toExternalForm());
			SpongebobManager thread = new SpongebobManager(root);
			thread.start();
			SpawnerPlanktonManager SpawnerManager = new SpawnerPlanktonManager(root);
			SpawnerManager.start();

			PrimaryStage.setMaxHeight(1000);
			PrimaryStage.setMaxWidth(500);
			PrimaryStage.setMinHeight(800);
			PrimaryStage.setMinWidth(400);
			PrimaryStage.setResizable(true);
			PrimaryStage.setScene(scene);
			PrimaryStage.sizeToScene();
			PrimaryStage.show();
			MediaPlayer musica = new MediaPlayer(new Media(new File("src/Images/SpongeBob_Soundtrack.mp3").toURI().toString()));

			musica.setVolume(100);

			musica.play();


			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
