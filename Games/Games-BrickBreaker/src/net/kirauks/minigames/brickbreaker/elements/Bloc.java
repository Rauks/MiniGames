/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker.elements;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.kirauks.minigames.brickbreaker.Game;

/**
 *
 * @author Karl
 */
public class Bloc extends Parent{
    public static final double SIZE = 40d;
    public static final double THICKNESS = 15d;
    
    public enum BlocType{
        BLUE(Color.BLUE, true, false),
        RED(Color.RED, true, false),
        GREEN(Color.GREEN, true, false),
        YELLOW(Color.YELLOW, true, false),
        SOLID(Color.GRAY, false, false),
        NULL(Game.COLOR_BACKGROUND, false, true);
        
        private final Color color;
        private final boolean breakable;
        private final boolean traversable;
        private BlocType(Color color, boolean breakable, boolean traversable){
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
        return type;
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
            this.view.setVisible(false);
            this.destoyed = true;
        }
    }
}
