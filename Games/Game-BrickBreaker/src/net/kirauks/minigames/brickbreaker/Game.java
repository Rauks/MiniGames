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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.kirauks.minigames.brickbreaker.audio.AudioManager;
import net.kirauks.minigames.brickbreaker.elements.level.Level;
import net.kirauks.minigames.brickbreaker.elements.level.LevelDescriptor.LevelDatas;
import net.kirauks.minigames.brickbreaker.elements.level.LevelHitListener;
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
    private static final Font FONT_PIXEL_40 = Font.loadFont(GameApplication.class.getResourceAsStream("res/Visitor.ttf"), 40);
    
    public static final Color COLOR_BACKGROUND = Color.BLACK;
    public static final Color COLOR_TEXT = Color.WHITE;
    public static final Color COLOR_GAMEOVER = Color.RED;
    public static final double STAGE_WIDTH = 400d;
    public static final double STAGE_HEIGHT = 600d;
    public static final double TEXT_BASELINE = 40d;
    public static final double TEXT_INSETS = 10d;
    public static final int LIVES = 5;
    
    private Group root;
    private Text gameOver;
    private Text scoreLabel;
    private Text livesLabel;
    private Level level;
    private int score = 0;
    private int lives = LIVES;
    
    private AudioManager audioManager;
    
    private LevelWinListener winListener;
    private LevelLoseListener loseListener;
    private LevelScoreListener scoreListener;
    private LevelLifeListener lifeListener;
    private LevelHitListener hitListener;
    
    
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
        
        this.gameOver = new Text("Game Over");
        this.gameOver.setFont(FONT_PIXEL_40);
        this.gameOver.setWrappingWidth(STAGE_WIDTH);
        this.gameOver.setTextAlignment(TextAlignment.CENTER);
        this.gameOver.setFill(COLOR_GAMEOVER);
        this.gameOver.setVisible(false);
        anchor.getChildren().add(this.gameOver);
        this.gameOver.setTranslateX(0d);
        this.gameOver.setTranslateY(STAGE_HEIGHT / 2d);
        
        BorderPane texts = new BorderPane();
        texts.setPadding(new Insets(TEXT_INSETS));
        texts.setMinWidth(STAGE_WIDTH);
        texts.setPrefWidth(STAGE_WIDTH);
        texts.setMaxWidth(STAGE_WIDTH);
        texts.setTranslateX(0d);
        texts.setTranslateY(STAGE_HEIGHT - TEXT_BASELINE);
        
        this.scoreLabel = new Text("Score : " + this.score);
        this.scoreLabel.setFont(FONT_PIXEL_20);
        this.scoreLabel.setFill(COLOR_TEXT);
        this.livesLabel = new Text("Lives : " + this.lives);
        this.livesLabel.setFont(FONT_PIXEL_20);
        this.livesLabel.setFill(COLOR_TEXT);
        
        texts.setLeft(this.scoreLabel);
        texts.setRight(this.livesLabel);
        anchor.getChildren().add(texts);
        
        this.lifeListener = new LevelLifeListener() {
            @Override
            public void onChange(int value) {
                Game.this.lives += value;
                Game.this.livesLabel.setText("Lives : " + Game.this.lives);
                Game.this.audioManager.playDie();
            }
        };
        this.scoreListener = new LevelScoreListener() {
            @Override
            public void onChange(int value) {
                Game.this.score += value;
                Game.this.scoreLabel.setText("Score : " + Game.this.score);
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
                Game.this.gameOver.setVisible(true);
                Game.this.level.stopDefinitly();
            }
        };
        this.hitListener = new LevelHitListener() {;
            @Override
            public void onHit(LevelHitListener.HitMarker marker) {
                switch(marker){
                    case BAR:
                        Game.this.audioManager.playBar();
                        break;
                    case BLOC_BREAK:
                        Game.this.audioManager.playBreak();
                        break;
                }
            }
        };
        
        Scene scene = new Scene(anchor);
        scene.setFill(COLOR_BACKGROUND);
        return scene;
    }

    @Override
    public void onEngineStart() {
        this.audioManager = AudioManager.getInstance();
    }

    @Override
    public void onGameStart() {
        this.audioManager.playBackgroundLoop();
        this.loadRandomLevel();
    }

    @Override
    public void onCloseStage() {
        this.audioManager.stopBackgroundLoop();
    }

    @Override
    public void onPauseGame() {
        this.level.pause();
        this.audioManager.stopBackgroundLoop();
    }

    @Override
    public void onResumeGame() {
        this.audioManager.playBackgroundLoop();
        this.level.resume();
    }

    @Override
    public void onActionKeyPressed() {}

    @Override
    public void onActionKeyReleased() {
        if(this.gameOver.isVisible()){
            this.gameOver.setVisible(false);
            this.lives = LIVES;
            this.livesLabel.setText("Lives : " + Game.this.lives);
            this.score = 0;
            this.scoreLabel.setText("Score : " + Game.this.score);
            this.loadRandomLevel();
        }
        else{
            this.level.launchBall();
        }
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
        this.level.setHitListener(this.hitListener);
        
        this.root.getChildren().add(this.level);
        this.level.start();
    }
}