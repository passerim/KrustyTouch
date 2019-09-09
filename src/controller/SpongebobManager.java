package controller;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SpongebobManager extends Thread {
    @FXML
    //class fields
    private static  SpongebobManager istanza = null;
    private ImageView Spongebob = new ImageView();
    private AnchorPane root;
    private int walking_position=0;
    private TranslateTransition movements = new TranslateTransition();

    public static synchronized SpongebobManager getSpongebobManager(AnchorPane base){
        if(istanza == null){
            istanza= new SpongebobManager(base);
        }
        return istanza;
    }
    //class builder
    private SpongebobManager (AnchorPane base) {
        this.root = base;
    }

    //run method of songebobManager
    public void run() {
        Spongebob.setImage(new Image("Images/walking_spongebob_1.png"));
        Spongebob.setPreserveRatio(true);
        Spongebob.setLayoutX(0);
        Spongebob.setLayoutY((root.getPrefHeight()*75)/100);
        Spongebob.setVisible(true);
        this.root.getChildren().add(Spongebob);
        this.root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double height= (double) newValue;
                double NewHeight = ((root.getHeight()/1000)*100);
                double NewWidth = ((root.getWidth()/500)*100);
                getSpongebob().setLayoutY((height*75)/100);
                getSpongebob().setFitHeight(227*NewHeight/100);
                getSpongebob().setFitWidth(188*NewWidth/100);
            }
        });
        movements.setNode(Spongebob);
        move_to_the_left();
        while(true) {
            try {
                sleep(250);
                if (walking_position == 0) {
                    Spongebob.setImage(new Image("Images/walking_spongebob_2.png"));
                    walking_position = 1;
                } else {
                    Spongebob.setImage(new Image("Images/walking_spongebob_1.png"));
                    walking_position = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void move_to_the_left(){
        Spongebob.setRotate(0);
        Spongebob.setRotationAxis(new Point3D(0, 0, 1));
        movements.setFromX(0);
        movements.setToX(300);
        movements.setDuration(Duration.millis(10000));
        movements.setOnFinished((event)->move_to_the_right());
        movements.play();
    }
    
    private void move_to_the_right(){
        Spongebob.setRotate(180);
        Spongebob.setRotationAxis(new Point3D(0, 180, 1));
        movements.setFromX(300);
        movements.setToX(0);
        movements.setDuration(Duration.millis(10000));
        movements.setOnFinished((event)->move_to_the_left());
        movements.play();
    }

    public ImageView getSpongebob() {
        return this.Spongebob;
    }

}


