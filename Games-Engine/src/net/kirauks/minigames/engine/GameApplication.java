/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.engine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Karl
 */
abstract class GameApplication extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(this.createStageTitle());
        
        Scene scene = this.createScene();
        stage.setScene(scene);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                GameApplication.this.onCloseStage();
            }
        });
        
        stage.show();
    }
    
    /**
     * Called on Stage creation, the returned String will be used as Stage's title.
     * 
     * @return The used stage title; 
     */
    public abstract String createStageTitle();
    
    /**
     * Called on Scene adding during the Stage creation process, the returned Scene will be added to the Scene.
     * 
     * @return 
     */
    public abstract Scene createScene();
    
    /**
     * Called on GameApplication close;
     */
    public abstract void onCloseStage();
    
    /**
     * Called on Game pause.
     */
    public abstract void onPauseGame();

    /**
     * Called on Game resume.
     */
    public abstract void onResumeGame();
}
