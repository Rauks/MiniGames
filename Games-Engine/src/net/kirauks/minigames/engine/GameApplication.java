/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.engine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.kirauks.minigames.engine.utils.Options;

/**
 *
 * @author Karl
 */
abstract class GameApplication extends Application{    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(this.createStageTitle());
        
        Scene scene = this.createScene();    
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if(key.equals(Options.getActionKey())){
                    GameApplication.this.onActionKeyPressed();
                }
                else if(key.equals(Options.getUpKey())){
                    GameApplication.this.onUpKeyPressed();
                }
                else if(key.equals(Options.getDownKey())){
                    GameApplication.this.onDownKeyPressed();
                }
                else if(key.equals(Options.getLeftKey())){
                    GameApplication.this.onLeftKeyPressed();
                }
                else if(key.equals(Options.getRightKey())){
                    GameApplication.this.onRightKeyPressed();
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if(key.equals(Options.getActionKey())){
                    GameApplication.this.onActionKeyReleased();
                }
            }
            
        });
        
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
    
    public abstract void onCloseStage();
    public abstract void onPauseGame();
    public abstract void onResumeGame();
    
    public abstract void onActionKeyPressed();
    public abstract void onActionKeyReleased();
    public abstract void onUpKeyPressed();
    public abstract void onDownKeyPressed();
    public abstract void onLeftKeyPressed();
    public abstract void onRightKeyPressed();
}
