package application;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public abstract class Bonus {

     public double RandomPosition(){
          double MaxBound=(Main.root.getWidth()*75)/100;
          double MinBound=(Main.root.getWidth()*15)/100;
          double location =(Math.random()*(MaxBound+MinBound))+MinBound;
          return location;
     }

     public void  Move(ImageView obj){
          TranslateTransition movement= new TranslateTransition();
          movement.setNode(obj);
          movement.setDuration(Duration.millis(3000));
          movement.setFromY(0);
          movement.setToY((Main.root.getHeight()*55)/100);
          movement.setOnFinished((event)-> Main.root.getChildren().remove(obj));
          movement.play();
     }

     public abstract void Spawn();

     public abstract void action();

}
