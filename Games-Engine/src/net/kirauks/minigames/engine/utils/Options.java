/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.engine.utils;

import javafx.scene.input.KeyCode;

/**
 *
 * @author Karl
 */
public class Options {
    public static KeyCode getUpKey(){
        return KeyCode.UP;
    }
    public static KeyCode getDownKey(){
        return KeyCode.DOWN;
    }
    public static KeyCode getLeftKey(){
        return KeyCode.LEFT;
    }
    public static KeyCode getRightKey(){
        return KeyCode.RIGHT;
    }
    public static KeyCode getActionKey(){
        return KeyCode.SPACE;
    }
    public static KeyCode getPauseKey(){
        return KeyCode.ESCAPE;
    }
}
