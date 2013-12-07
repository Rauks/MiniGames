/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker.audio;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;
import net.kirauks.minigames.brickbreaker.Game;

/**
 *
 * @author Karl
 */
public class AudioManager {
    private AudioClip background;
    private AudioClip die;
    private AudioClip bloc;
    private AudioClip bar;
    
    private static AudioManager INSTANCE;
    public static AudioManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new AudioManager();
        }
        return INSTANCE;
    }
    
    private AudioManager(){
        try {
            this.background = new AudioClip(Game.class.getResource("res/Main.wav").toURI().toString());
            this.background.setCycleCount(AudioClip.INDEFINITE);
            
            this.die = new AudioClip(Game.class.getResource("res/Die.wav").toURI().toString());
            
            this.bloc = new AudioClip(Game.class.getResource("res/Break.wav").toURI().toString());
            
            this.bar = new AudioClip(Game.class.getResource("res/Jump.wav").toURI().toString());
        } catch (URISyntaxException ex) {
            Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public void playBackgroundLoop(){
        this.background.play();
    }
    public void stopBackgroundLoop(){
        this.background.stop();
    }
    
    public void playDie(){
        this.die.play();
    }
    
    public void playBreak(){
        this.bloc.play();
    }
    
    public void playBar(){
        this.bar.play();
    }
    
    public void stopAll(){
        this.background.stop();
        this.bar.stop();
        this.bloc.stop();
        this.die.stop();
    }
}
