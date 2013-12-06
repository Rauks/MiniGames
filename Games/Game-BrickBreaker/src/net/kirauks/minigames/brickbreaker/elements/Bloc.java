/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker.elements;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import net.kirauks.minigames.brickbreaker.Game;

/**
 *
 * @author Karl
 */
public class Bloc extends Parent{
    public static final double SIZE = 40d;
    public static final double THICKNESS = 15d;
    
    public enum BlocType{
        BLUE(25, Color.BLUE, true, false),
        RED(50, Color.RED, true, false),
        GREEN(75, Color.GREEN, true, false),
        YELLOW(100, Color.YELLOW, true, false),
        SOLID(0, Color.GRAY, false, false),
        NULL(0, Game.COLOR_BACKGROUND, false, true);
        
        private final Color color;
        private final boolean breakable;
        private final boolean traversable;
        private int breakScore;
        private BlocType(int breakScore, Color color, boolean breakable, boolean traversable){
            this.breakScore = breakScore;
            this.color = color;
            this.breakable = breakable;
            this.traversable = traversable;
        }
    }
    
    private final BlocType type;
    private final Node view;
    private boolean destoyed;
    
    public Bloc(BlocType type){
        this.type = type;
        this.destoyed = false;
        
        Rectangle rectangle = new Rectangle(SIZE, THICKNESS);
        rectangle.setStroke(this.type.color);
        
        this.view = rectangle;
        this.getChildren().add(this.view);
    }
    
    public BlocType getType(){
        return this.type;
    }
    public int getScoreValue(){
        return this.type.breakScore;
    }
    
    public boolean destroyed(){
        return this.destoyed;
    }
    
    public boolean traversable(){
        return this.type.traversable;
    }
    public boolean breakable(){
        return this.type.breakable;
    }
    
    public void destroy(){
        if(this.type.breakable){
            this.destoyed = true;
            FadeTransition fade = new FadeTransition(Duration.millis(300));
            fade.setNode(this.view);
            fade.setFromValue(1d);
            fade.setToValue(0d);
            fade.play();
        }
    }
}
