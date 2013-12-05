/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker.elements.level;

import java.util.Random;
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
    private double moveBallX;
    private double moveBallY;
    
    private Timeline timeline;
    private Random random = new Random();
    
    public Level(){
        this.bar = new Bar();
        this.ball = new Ball();
        this.resetLevel();
        
        this.getChildren().addAll(this.bar, this.ball);
        
        this.buildTimeline();
    }
    
    private void resetLevel(){
        this.bar.setTranslateX(Game.STAGE_WIDTH / 2d - Bar.SIZE / 2d);
        this.bar.setTranslateY(Game.STAGE_HEIGHT - Bar.BASELINE - Bar.THICKNESS / 2d);
        this.moveBar = 0;
        
        this.ball.setTranslateX(Game.STAGE_WIDTH / 2d);
        this.ball.setTranslateY(Game.STAGE_HEIGHT - Bar.BASELINE - Bar.THICKNESS / 2d - Ball.RADIUS);
        this.ballState = BallState.CATCHED;
        this.moveBallX = (random.nextDouble() - 0.5) * Ball.SPEED_MIN;
        this.moveBallY = -Ball.SPEED_MIN;
    }
    
    private void buildTimeline(){
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        
        KeyFrame k = new KeyFrame(ANIMATION_TIMELINE_TIME, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //Bar moves
                if(Level.this.moveBar != 0){
                    Level.this.moveBar();
                }
                
                //Ball moves
                if(Level.this.ballState == BallState.MOVING){
                    Level.this.moveBall();
                }
            }
        });
        this.timeline.getKeyFrames().add(k);
    }
    
    private void moveBar(){
        double toX = this.bar.getTranslateX() + this.moveBar;
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
                ballX = Ball.RADIUS;
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
        this.moveBar = -Bar.SPEED;
    }
    
    public void moveBarRight(){
        this.moveBar = Bar.SPEED;
    }
    
    private void moveBall(){
        double newX = this.ball.getTranslateX() + this.moveBallX;
        double newY = this.ball.getTranslateY() + this.moveBallY;
        boolean reverseX = false;
        boolean reverseY = false;
        if(newX < Ball.RADIUS){
            newX = -newX;
            reverseX = true;
        }
        double ballMaxX = Game.STAGE_WIDTH - Ball.RADIUS;
        if(newX > ballMaxX){
            newX = ballMaxX - (newX - ballMaxX);
            reverseX = true;
        }
        if(newY < Ball.RADIUS){
            newY = -newY;
            reverseY = true;
        }
        double ballMaxY = Game.STAGE_HEIGHT -  - Ball.RADIUS;
        if(newY > ballMaxY){
            newY = ballMaxY - (newY - ballMaxY);
            reverseY = true;
        }
        
        this.ball.setTranslateX(newX);
        this.ball.setTranslateY(newY);
        
        if(reverseX){
            this.moveBallX = -this.moveBallX;
        }
        if(reverseY){
            this.moveBallY = -this.moveBallY;
        }
    }
    
    public void launchBall(){
        this.ballState = BallState.MOVING;
    }
    
    public void start(){
        this.timeline.play();
    }
}
