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

/**
 *
 * @author Karl
 */
public class Bloc extends Parent{
    public static final double SIZE = 30d;
    public static final double THICKNESS = 10d;
    
    public enum BlocType{
        BLUE(Color.BLUE),
        RED(Color.RED),
        GREEN(Color.GREEN),
        YELLOW(Color.YELLOW),
        GRAY(Color.GRAY),
        NULL(Color.BLACK);
        
        private final Color color;
        private BlocType(Color color){
            this.color = color;
        }
    }
    
    private BlocType type;
    private Node view;
    
    public Bloc(BlocType type){
        this.type = type;
        
        Rectangle rectangle = new Rectangle(SIZE, THICKNESS);
        rectangle.setStroke(this.type.color);
        
        this.view = rectangle;
        this.getChildren().add(this.view);
    }
    
    public BlocType getType(){
        return type;
    }
}
