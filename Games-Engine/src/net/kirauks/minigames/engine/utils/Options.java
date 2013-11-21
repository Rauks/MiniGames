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
    public Options(){}
    
    public KeyCode getUpKey(){
        return KeyCode.UP;
    }
    public KeyCode getDownKey(){
        return KeyCode.DOWN;
    }
    public KeyCode getLeftKey(){
        return KeyCode.LEFT;
    }
    public KeyCode getRightKey(){
        return KeyCode.RIGHT;
    }
    public KeyCode getActionKey(){
        return KeyCode.SPACE;
    }
    public KeyCode getPauseKey(){
        return KeyCode.ESCAPE;
    }
}
