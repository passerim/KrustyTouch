package controller;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SpongebobManager extends Thread {
    
    private static SpongebobManager istanza = null;
    private final ImageView Spongebob = new ImageView();
    private final AnchorPane root;
    private int walking_position = 0;
    private final TranslateTransition movements = new TranslateTransition();

    public static synchronized SpongebobManager getSpongebobManager(final AnchorPane base){
        if(istanza == null){
            istanza= new SpongebobManager(base);
        }
        return istanza;
    }
    
    private SpongebobManager (final AnchorPane base) {
        this.root = base;
    }

    public void run() {
        this.Spongebob.setImage(new Image(ClassLoader.getSystemResource("images/walking_spongebob_1.png").toString()));
        this.Spongebob.setPreserveRatio(true);
        this.Spongebob.setLayoutX(0);
        this.Spongebob.setLayoutY((this.root.getPrefHeight()*75)/100);
        this.Spongebob.setVisible(true);
        this.root.getChildren().add(this.Spongebob);
        this.root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double height= (double) newValue;
                double NewHeight = ((SpongebobManager.this.root.getHeight()/1000)*100);
                double NewWidth = ((SpongebobManager.this.root.getWidth()/500)*100);
                getSpongebob().setLayoutY((height*75)/100);
                getSpongebob().setFitHeight(227*NewHeight/100);
                getSpongebob().setFitWidth(188*NewWidth/100);
            }
        });
        movements.setNode(this.Spongebob);
        firstMove();
        while(true) {
            try {
                sleep(250);
                if (this.walking_position == 0) {
                    this.Spongebob.setImage(new Image(ClassLoader.getSystemResource("images/walking_spongebob_2.png").toString()));
                    this.walking_position = 1;
                } else {
                    this.Spongebob.setImage(new Image(ClassLoader.getSystemResource("images/walking_spongebob_1.png").toString()));
                    this.walking_position = 0;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void firstMove(){
        this.Spongebob.setRotate(0);
        this.Spongebob.setRotationAxis(new Point3D(0, 0, 1));
        this.movements.setFromX(0);
        this.movements.setToX(0);
        this.movements.setDuration(Duration.millis(1000));
        this.movements.setOnFinished((event)->moveRight());
        this.movements.play();
    }
    
    private void moveRight(){
        this.Spongebob.setRotate(0);
        this.Spongebob.setRotationAxis(new Point3D(0, 0, 1));
        this.movements.setFromX(0);
        this.movements.setToX(this.root.getWidth()-this.Spongebob.getFitWidth());
        this.movements.setDuration(Duration.millis(10000));
        this.movements.setOnFinished((event)->moveLeft());
        this.movements.play();
    }
    
    private void moveLeft(){
        this.Spongebob.setRotate(180);
        this.Spongebob.setRotationAxis(new Point3D(0, 180, 1));
        this.movements.setFromX(this.root.getWidth()-this.Spongebob.getFitWidth());
        this.movements.setToX(0);
        this.movements.setDuration(Duration.millis(10000));
        this.movements.setOnFinished((event)->moveRight());
        this.movements.play();
    }

    public ImageView getSpongebob() {
        return this.Spongebob;
    }

}


