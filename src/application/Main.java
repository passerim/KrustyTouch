package application;
	

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

			//starting the adaptive background image
			ImageView background = new ImageView(new Image("Images/sfondo_FINALE.png"));

			background.setPreserveRatio(false);
			background.setSmooth(true);
			background.setVisible(true);
			background.fitWidthProperty().bind(root.widthProperty());
			background.fitHeightProperty().bind(root.heightProperty());



			//starting spongebob
			SpongebobManager spongebob = new SpongebobManager(root);
			spongebob.start();

			//starting plankton
			SpawnerPlanktonManager SpawnerManager = new SpawnerPlanktonManager(root);
			SpawnerManager.start();



			root.setMaxSize(500,1000);
			root.setMinSize(400,800);
			root.setPrefHeight(1000);
			root.setPrefWidth(500);
			PrimaryStage.setMaxHeight(1000);
			PrimaryStage.setMaxWidth(500);
			PrimaryStage.setMinHeight(800);
			PrimaryStage.setMinWidth(400);
			PrimaryStage.setResizable(true);

			root.getChildren().add(background);
			PrimaryStage.setScene(scene);
			PrimaryStage.sizeToScene();
			MediaPlayer musica = new MediaPlayer(new Media(new File("src/Images/SpongeBob_Soundtrack.mp3").toURI().toString()));

			musica.setVolume(100);

			musica.play();

			PrimaryStage.show();


			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
