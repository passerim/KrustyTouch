package application;



//import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

public class SpawnerPlanktonManager extends Thread{
	
Pane root;


//PlanktonManager thread2;
	public SpawnerPlanktonManager(Pane base) {
		this.root=base;
	}
	@Override
public void run() {

while(true) {

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
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
	
	
}
/*public PlanktonManager creator() {
	return new PlanktonManager(root);
}*/
}
