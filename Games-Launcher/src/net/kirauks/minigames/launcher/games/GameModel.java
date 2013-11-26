/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.launcher.games;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Karl
 */
public class GameModel {
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleStringProperty url;
    
    public GameModel(String name, String description, String url){
        this.name = new SimpleStringProperty(name);
        this.name = new SimpleStringProperty(description);
        this.name = new SimpleStringProperty(url);
    }
    
    public ReadOnlyStringProperty nameProperty(){
        return this.name;
    }
    public ReadOnlyStringProperty descriptionProperty(){
        return this.description;
    }
    public ReadOnlyStringProperty urlProperty(){
        return this.url;
    }
}
