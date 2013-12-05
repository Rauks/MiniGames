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
public class Bar extends Parent{
    public static final double SIZE = 30d;
    public static final double THICKNESS = 10d;
    public static final Color COLOR = Color.WHITE;
    public static final double BASELINE = 40d;
    public static final double SPEED = 10d;
    
    private final Node view;
    
    public Bar(){
        Rectangle rectangle = new Rectangle(SIZE, THICKNESS);
        rectangle.setStroke(COLOR);
        
        this.view = rectangle;
        this.getChildren().add(this.view);
    }
}
