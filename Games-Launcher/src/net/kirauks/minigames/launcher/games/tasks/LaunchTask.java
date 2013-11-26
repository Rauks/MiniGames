/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.launcher.games.tasks;

import javafx.concurrent.Task;

/**
 *
 * @author Karl
 */
public class LaunchTask extends Task<Void>{
    private final ProcessBuilder pb;
    
    public LaunchTask(ProcessBuilder pb){
        this.pb = pb;
    }

    @Override
    protected Void call() throws Exception {
        Process p = pb.start();
        p.waitFor();
        return null;
    }
}
