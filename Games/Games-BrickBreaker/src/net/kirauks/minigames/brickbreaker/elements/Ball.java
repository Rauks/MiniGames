/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker.elements;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Karl
 */
public class Ball extends Parent{
    public static final double RADIUS = 2d;
    public static final Color COLOR = Color.WHITE;
    public static final double SPEED_MIN = 5d;
    public static final double SPEED_MAX = 10d;
    
    private final Node view;
    
    public Ball(){
        Circle circle = new Circle(RADIUS);
        circle.setStroke(COLOR);
        
        this.view = circle;
        this.getChildren().add(this.view);
    }
}
