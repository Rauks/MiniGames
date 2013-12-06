/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import net.kirauks.minigames.brickbreaker.elements.level.Level;
import net.kirauks.minigames.brickbreaker.elements.level.LevelDescriptor;
import net.kirauks.minigames.brickbreaker.elements.level.LevelDescriptor.LevelDatas;
import net.kirauks.minigames.brickbreaker.elements.level.LevelLifeListener;
import net.kirauks.minigames.brickbreaker.elements.level.LevelLoseListener;
import net.kirauks.minigames.brickbreaker.elements.level.LevelScoreListener;
import net.kirauks.minigames.brickbreaker.elements.level.LevelWinListener;
import net.kirauks.minigames.engine.GameApplication;

/**
 *
 * @author Karl
 */
public class Game extends GameApplication{
    private static final Font FONT_PIXEL_20 = Font.loadFont(GameApplication.class.getResourceAsStream("res/Visitor.ttf"), 20);
    
    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final Color COLOR_TEXT = Color.WHITE;
    public static final double STAGE_WIDTH = 400d;
    public static final double STAGE_HEIGHT = 600d;
    public static final double TEXT_BASELINE = 40d;
    public static final double TEXT_INSETS = 10d;
    public static final int LIVES = 5;
    
    private Group root;
    private Level level;
    private AudioClip mainAudio;
    private int score = 0;
    private int lives = LIVES;
    
    private LevelWinListener winListener;
    private LevelLoseListener loseListener;
    private LevelScoreListener scoreListener;
    private LevelLifeListener lifeListener;
    
    
    @Override
    public String createStageTitle() {
        return "Casse Briques";
    }

    @Override
    public Scene createScene() {
        this.root = new Group();
        
        AnchorPane anchor = new AnchorPane();
        anchor.getChildren().add(this.root);
        anchor.setMinWidth(STAGE_WIDTH);
        anchor.setMinHeight(STAGE_HEIGHT);
        anchor.setPrefWidth(STAGE_WIDTH);
        anchor.setPrefHeight(STAGE_HEIGHT);
        anchor.setMaxWidth(STAGE_WIDTH);
        anchor.setMaxHeight(STAGE_HEIGHT);
        
        BorderPane texts = new BorderPane();
        texts.setPadding(new Insets(TEXT_INSETS));
        texts.setMinWidth(STAGE_WIDTH);
        texts.setPrefWidth(STAGE_WIDTH);
        texts.setMaxWidth(STAGE_WIDTH);
        texts.setTranslateX(0d);
        texts.setTranslateY(STAGE_HEIGHT - TEXT_BASELINE);
        
        final Label scoreLabel = new Label("Score : " + this.score);
        scoreLabel.setFont(FONT_PIXEL_20);
        scoreLabel.setTextFill(COLOR_TEXT);
        final Label livesLabel = new Label("Lives : " + this.lives);
        livesLabel.setFont(FONT_PIXEL_20);
        livesLabel.setTextFill(COLOR_TEXT);
        
        texts.setLeft(scoreLabel);
        texts.setRight(livesLabel);
        anchor.getChildren().add(texts);
        
        this.lifeListener = new LevelLifeListener() {
            @Override
            public void onChange(int value) {
                Game.this.lives += value;
                livesLabel.setText("Lives : " + Game.this.lives);
            }
        };
        this.scoreListener = new LevelScoreListener() {
            @Override
            public void onChange(int value) {
                Game.this.score += value;
                scoreLabel.setText("Score : " + Game.this.score);
            }
        };
        this.winListener = new LevelWinListener() {
            @Override
            public void onWin() {
                Game.this.loadRandomLevel();
            }
        };
        this.loseListener = new LevelLoseListener() {
            @Override
            public void onLose() {
                Game.this.level.stopDefinitly();
            }
        };
        
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
        this.loadRandomLevel();
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
    
    private final Random random = new Random();
    private void loadRandomLevel(){
        if(this.level != null){
            this.root.getChildren().remove(this.level);
        }
        
        List<LevelDatas> levels = Collections.unmodifiableList(Arrays.asList(LevelDatas.values()));
        this.level = new Level(levels.get(this.random.nextInt(levels.size())), this.lives);
        
        this.level.setLifeListener(this.lifeListener);
        this.level.setScoreListener(this.scoreListener);
        this.level.setWinListener(this.winListener);
        this.level.setLoseListener(this.loseListener);
        
        this.root.getChildren().add(this.level);
        this.level.start();
    }
}