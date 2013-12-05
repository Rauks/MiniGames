/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import net.kirauks.minigames.engine.GameApplication;

/**
 *
 * @author Karl
 */
public class Game extends GameApplication{
    private static final Color COLOR_BACKGROUND = Color.BLACK;
    
    private Group root;
    
    
    @Override
    public String createStageTitle() {
        return "Casse Briques";
    }

    @Override
    public Scene createScene() {
        this.root = new Group();
        
        AnchorPane anchor = new AnchorPane();
        anchor.getChildren().add(this.root);
        anchor.setPrefHeight(600d);
        anchor.setPrefWidth(400d);
        
        Scene scene = new Scene(anchor);
        scene.setFill(COLOR_BACKGROUND);
        return scene;
    }

    @Override
    public void onCloseStage() {
        
    }

    @Override
    public void onPauseGame() {
        
    }

    @Override
    public void onResumeGame() {
        
    }

    @Override
    public void onActionKeyPressed() {
        
    }

    @Override
    public void onActionKeyReleased() {
        
    }

    @Override
    public void onUpKeyPressed() {
        
    }

    @Override
    public void onDownKeyPressed() {
        
    }

    @Override
    public void onLeftKeyPressed() {
        
    }

    @Override
    public void onRightKeyPressed() {
        
    }
    
}
