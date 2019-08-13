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

	@Override
	public void run() {

		while (true) {

			try {
				Thread.sleep(2000);
				Platform.runLater(() -> {
					PlanktonManager plankton = new PlanktonManager(root);
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

		Set<Node> prova;
		Iterator ciccia;
		Node priscilla;
		prova=root.getChildren()
				.stream()
				.filter(e->equals(""))
				.collect(Collectors.toSet());
		ciccia=prova.iterator();
		prova.forEach(nodo->nodo.prefHeight(NewHeight));
		prova.forEach(nodo->nodo.prefWidth(NewWidth));


	}
}
