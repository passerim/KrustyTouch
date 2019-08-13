package application;
	

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextFormatter;
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
			AnchorPane root= FXMLLoader.load(getClass().getResource("Scena.fxml"));
			Scene scene = new Scene(root);


			//starting the adaptive background image
			ImageView background = new ImageView(new Image("Images/sfondo_FINALE.png"));

			background.setPreserveRatio(true);
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

			root.heightProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					double height= (double) newValue;
					double NewHeight = ((scene.getHeight()/1000)*100);
					double NewWidth = ((scene.getWidth()/500)*100);
					spongebob.Spongebob.setLayoutY((height*75)/100);
					spongebob.Spongebob.setFitHeight(227*NewHeight/100);
					spongebob.Spongebob.setFitWidth(188*NewWidth/100);
					SpawnerManager.rearrange(NewHeight,NewWidth);

					System.out.println(spongebob.Spongebob.getFitHeight());
					System.out.println(spongebob.Spongebob.getFitWidth());
					System.out.println("altezza della finestra "+root.heightProperty().get());
					System.out.println("larghezza della finestra "+root.widthProperty().get());


				}
			});

			root.setMaxSize(500,1000);
			root.setMinSize(400,800);
			PrimaryStage.setTitle("Krusty Touch");
			PrimaryStage.setMaxHeight(1039);
			PrimaryStage.setMaxWidth(516);
			PrimaryStage.setMinHeight(839);
			PrimaryStage.setMinWidth(416);
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

