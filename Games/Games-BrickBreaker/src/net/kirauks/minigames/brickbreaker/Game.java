/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import net.kirauks.minigames.brickbreaker.elements.level.Level;
import net.kirauks.minigames.brickbreaker.elements.level.LevelDescriptor;
import net.kirauks.minigames.engine.GameApplication;

/**
 *
 * @author Karl
 */
public class Game extends GameApplication{
    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final double STAGE_WIDTH = 400d;
    public static final double STAGE_HEIGHT = 600d;
    
    private Group root;
    private Level level;
    private AudioClip mainAudio;
    
    
    @Override
    public String createStageTitle() {
        return "Casse Briques";
    }

    @Override
    public Scene createScene() {
        this.root = new Group();
        
        AnchorPane anchor = new AnchorPane();
        anchor.getChildren().add(this.root);
        anchor.setPrefWidth(STAGE_WIDTH);
        anchor.setPrefHeight(STAGE_HEIGHT);
        
        Scene scene = new Scene(anchor);
        scene.setFill(COLOR_BACKGROUND);
        return scene;
    }

    @Override
    public void onEngineStart() {
        try {
            this.mainAudio = new AudioClip(Game.class.getResource("res/Main.wav").toURI().toString());
        } catch (URISyntaxException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        this.mainAudio.setCycleCount(AudioClip.INDEFINITE);
    }

    @Override
    public void onGameStart() {
        this.mainAudio.play();
        this.level = new Level(LevelDescriptor.LevelDatas.L1);
        this.root.getChildren().add(this.level);
        this.level.start();
    }

    @Override
    public void onCloseStage() {
        this.mainAudio.stop();
    }

    @Override
    public void onPauseGame() {
        this.level.pause();
        this.mainAudio.setVolume(0.1d);
    }

    @Override
    public void onResumeGame() {
        this.mainAudio.setVolume(1d);
        this.level.resume();
    }

    @Override
    public void onActionKeyPressed() {}

    @Override
    public void onActionKeyReleased() {
        this.level.launchBall();
    }

    @Override
    public void onUpKeyPressed() {}

    @Override
    public void onUpKeyReleased() {}

    @Override
    public void onDownKeyPressed() {}

    @Override
    public void onDownKeyReleased() {}

    @Override
    public void onLeftKeyPressed() {
        this.level.moveBarLeft();
    }

    @Override
    public void onLeftKeyReleased() {
        this.level.stopMoveBar();
    }

    @Override
    public void onRightKeyPressed() {
        this.level.moveBarRight();
    }

    @Override
    public void onRightKeyReleased() {
        this.level.stopMoveBar();
    }   
}