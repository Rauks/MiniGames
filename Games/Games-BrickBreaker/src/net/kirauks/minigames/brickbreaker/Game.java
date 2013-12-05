/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import net.kirauks.minigames.engine.GameApplication;

/**
 *
 * @author Karl
 */
public class Game extends GameApplication{

    @Override
    public String createStageTitle() {
        return "Casse Briques";
    }

    @Override
    public Scene createScene() {
        return new Scene(new StackPane());
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
