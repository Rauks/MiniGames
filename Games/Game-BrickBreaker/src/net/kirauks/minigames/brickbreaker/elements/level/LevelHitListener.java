/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker.elements.level;

/**
 *
 * @author Karl
 */
public interface LevelHitListener {
    public enum HitMarker{
        BLOC_BREAK, BLOC_UNBREAK, BAR, WINDOW;
    }
    
    public void onHit(HitMarker marker);
}
