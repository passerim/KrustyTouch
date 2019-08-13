package application;



//import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class SpawnerPlanktonManager extends Thread {

	AnchorPane root;


	public SpawnerPlanktonManager(AnchorPane base) {
		this.root = base;
	}
	Set<PlanktonManager> prova;
	@Override
	public void run() {

		while (true) {

			try {
				Thread.sleep(2000);
				Platform.runLater(() -> {
					PlanktonManager plankton = new PlanktonManager(root);
					prova.add(plankton);
					root.getChildren().add(plankton.Plankton);
					plankton.start();

				});

				//thread2 =new PlanktonManager(this.root);

				//thread2.start();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}


	}

	public void rearrange(double NewHeight, double NewWidth) {
		prova.forEach(elem->elem.Plankton.setFitWidth(162*NewWidth/100));
		prova.forEach(elem->elem.Plankton. setFitHeight(142*NewHeight/100));

	}
}
