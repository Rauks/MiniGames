/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.engine;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import net.kirauks.minigames.engine.utils.Options;

/**
 *
 * @author Karl
 */
public abstract class GameApplication extends Application{
    private BorderPane pausePane = new BorderPane();
    private final static double PAUSE_OPACITY = 0.8d;
    
    @Override
    final public void start(Stage stage) throws Exception {
        stage.setTitle(this.createStageTitle());
        
        StackPane rootPane = new StackPane();
        this.pausePane.setStyle("-fx-background-color: #000000;");
        this.pausePane.setFocusTraversable(true);
        this.pausePane.setOpacity(0.0);
        this.pausePane.setMouseTransparent(true);
        Text pauseText = new Text("PAUSE");
        pauseText.opacityProperty().bind(this.pausePane.opacityProperty().multiply(1.0d / PAUSE_OPACITY));
        pauseText.setFill(Color.WHITE);
        this.pausePane.setCenter(pauseText);
        
        Scene scene = this.createScene();
        Parent sceneRoot = scene.getRoot();
        if(sceneRoot != null){
            rootPane.getChildren().add(sceneRoot);
        }
        rootPane.getChildren().add(pausePane);
        scene.setRoot(rootPane);
        
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
                else if(key.equals(Options.getPauseKey())){
                    GameApplication.this.togglePause();
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
    
    private boolean paused = false;
    FadeTransition pauseFade = new FadeTransition(Duration.seconds(0.2d), this.pausePane);
    private void togglePause() {
        if(this.paused){
            this.pauseFade.setFromValue(PAUSE_OPACITY);
            this.pauseFade.setToValue(0.0d);
            this.pauseFade.play();
            this.pausePane.setMouseTransparent(true);
            this.onResumeGame();
        }
        else{
            this.onPauseGame();
            this.pausePane.setMouseTransparent(false);
            this.pauseFade.setFromValue(0.0d);
            this.pauseFade.setToValue(PAUSE_OPACITY);
            this.pauseFade.play();
        }
        this.paused = !this.paused;
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
