/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker.elements.level;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.util.Duration;
import net.kirauks.minigames.brickbreaker.Game;
import net.kirauks.minigames.brickbreaker.elements.Ball;
import net.kirauks.minigames.brickbreaker.elements.Bar;

/**
 *
 * @author Karl
 */
public class Level extends Parent{
    public static final Duration ANIMATION_TIMELINE_TIME = Duration.millis(40);
    
    private enum BallState{
        CATCHED, MOVING;
    }
    
    private Bar bar;
    private double moveBar;
    private Ball ball;
    private BallState ballState;
    
    private Timeline timeline;
    
    public Level(){
        this.bar = new Bar();
        this.bar.setTranslateX(Game.STAGE_WIDTH / 2d - Bar.SIZE / 2d);
        this.bar.setTranslateY(Game.STAGE_HEIGHT - Bar.BASELINE - Bar.THICKNESS / 2d);
        this.moveBar = 0;
        
        this.ball = new Ball();
        this.ball.setTranslateX(Game.STAGE_WIDTH / 2d);
        this.ball.setTranslateY(Game.STAGE_HEIGHT - Bar.BASELINE - Bar.THICKNESS / 2d - Ball.RADIUS);
        this.ballState = BallState.CATCHED;
        
        this.getChildren().addAll(this.bar, this.ball);
        
        this.buildTimeline();
    }
    
    private void buildTimeline(){
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        
        KeyFrame k = new KeyFrame(ANIMATION_TIMELINE_TIME, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //Bar moves
                if(Level.this.moveBar != 0){
                    Level.this.moveBar(Level.this.bar.getTranslateX() + Level.this.moveBar);
                }
            }
        });
        this.timeline.getKeyFrames().add(k);
    }
    
    private void moveBar(double toX){
        if(toX < 0){
            toX = 0;
        }
        double barMax = Game.STAGE_WIDTH - Bar.SIZE;
        if(toX > barMax){
            toX = barMax;
        }
        if(this.ballState == BallState.CATCHED){
            double ballX = this.ball.getTranslateX() + toX - this.bar.getTranslateX();
            if(ballX < Ball.RADIUS){
                ballX = 0;
            }
            double ballMax = Game.STAGE_WIDTH - Ball.RADIUS;
            if(ballX > ballMax){
                ballX = ballMax;
            }
            this.ball.setTranslateX(ballX);
        }
        this.bar.setTranslateX(toX);
    }
    
    public void stopMoveBar(){
        this.moveBar = 0d;
    }
    
    public void moveBarLeft(){
        this.moveBar = -Bar.SPEED_X;
    }
    
    public void moveBarRight(){
        this.moveBar = Bar.SPEED_X;
    }
    
    public void start(){
        this.timeline.play();
    }
}
