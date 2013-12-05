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
import javafx.scene.text.Font;
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
    private static final Font FONT_PIXEL_40 = Font.loadFont(GameApplication.class.getResourceAsStream("res/Visitor.ttf"), 40);
    
    private final BorderPane pausePane = new BorderPane();
    private Stage stage;
    private final static double PAUSE_OPACITY = 0.8d;
    
    public void setScene(Scene scene){
        StackPane rootPane = new StackPane();
        Parent sceneRoot = scene.getRoot();
        if(sceneRoot != null){
            rootPane.getChildren().add(sceneRoot);
        }
        rootPane.getChildren().add(this.pausePane);
        scene.setRoot(rootPane);
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if(!GameApplication.this.paused && key.equals(Options.getActionKey())){
                    GameApplication.this.onActionKeyPressed();
                }
                else if(!GameApplication.this.paused && key.equals(Options.getUpKey())){
                    GameApplication.this.onUpKeyPressed();
                }
                else if(!GameApplication.this.paused && key.equals(Options.getDownKey())){
                    GameApplication.this.onDownKeyPressed();
                }
                else if(!GameApplication.this.paused && key.equals(Options.getLeftKey())){
                    GameApplication.this.onLeftKeyPressed();
                }
                else if(!GameApplication.this.paused && key.equals(Options.getRightKey())){
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
                if(!GameApplication.this.paused && key.equals(Options.getActionKey())){
                    GameApplication.this.onActionKeyReleased();
                }
            }
            
        });
        this.stage.setScene(scene);
    }
    
    @Override
    final public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle(this.createStageTitle());
        
        this.pausePane.setStyle("-fx-background-color: #000000;");
        this.pausePane.setFocusTraversable(true);
        this.pausePane.setOpacity(0.0);
        this.pausePane.setMouseTransparent(true);
        Text pauseText = new Text("PAUSE");
        pauseText.setFont(FONT_PIXEL_40);
        pauseText.opacityProperty().bind(this.pausePane.opacityProperty().multiply(1.0d / PAUSE_OPACITY));
        pauseText.setFill(Color.WHITE);
        this.pausePane.setCenter(pauseText);
        
        this.setScene(this.createScene());
        
        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                GameApplication.this.onCloseStage();
            }
        });
        
        this.stage.show();
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
