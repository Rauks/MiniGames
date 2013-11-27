/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.launcher.games;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 *
 * @author Karl
 */
public class GameModel implements Externalizable{
    private static final long serialVersionUID = 1L;
    
    private SimpleStringProperty name;
    private SimpleStringProperty description;
    private SimpleStringProperty path;
    private SimpleStringProperty uuid;
    private Duration playtime;
    private SimpleDoubleProperty playtimeMinutes;
    private SimpleDoubleProperty playtimeHours;
    private String splashUrl;
    private SimpleObjectProperty<Image> splash;
    
    public GameModel(){
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.path = new SimpleStringProperty();
        this.uuid = new SimpleStringProperty();
        this.playtime = Duration.ZERO;
        this.playtimeHours = new SimpleDoubleProperty(0d);
        this.playtimeMinutes = new SimpleDoubleProperty(0d);
        this.splash = new SimpleObjectProperty<>(null);
        
    }
    public GameModel(String name, String description, String gameUrl, String splashUrl, String uuid){
        this();
        this.name.setValue(name);
        this.description.setValue(description);
        this.path.setValue(gameUrl);
        this.uuid.setValue(uuid);
        this.splashUrl = splashUrl;
        this.loadSplash();
    }
    
    private void loadSplash(){
        if(this.splashUrl != null){
            this.splash.setValue(new Image(this.splashUrl));
        }
    }
    
    public ReadOnlyStringProperty nameProperty(){
        return this.name;
    }
    public ReadOnlyStringProperty descriptionProperty(){
        return this.description;
    }
    public ReadOnlyStringProperty pathProperty(){
        return this.path;
    }
    public ReadOnlyStringProperty uuidProperty(){
        return this.uuid;
    }
    public ReadOnlyDoubleProperty playtimeMinutesProperty(){
        return this.playtimeMinutes;
    }
    public ReadOnlyDoubleProperty playtimeHoursProperty(){
        return this.playtimeHours;
    }
    public ReadOnlyObjectProperty<Image> splashProperty(){
        return this.splash;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }
    public void setDescription(String description) {
        this.description.setValue(description);
    }
    public void setPath(String path) {
        this.path.setValue(path);
    }
    public void setUuid(String uuid) {
        this.uuid.setValue(uuid);
    }
    public void setSplash(Image splash){
        this.splash.setValue(splash);
    }
    
    public void addToPlaytime(Duration duration){
        this.playtime = this.playtime.add(duration);
        this.playtimeMinutes.setValue(this.playtime.toMinutes());
        this.playtimeHours.setValue(this.playtime.toHours());
    }
    
    public String getName(){
        return this.name.getValue();
    }
    public String getDescription(){
        return this.description.getValue();
    }
    public String getPath(){
        return this.path.getValue();
    }
    public String getUuid(){
        return this.uuid.getValue();
    }
    public Double getPlaytimeMinutes(){
        return this.playtimeMinutes.getValue();
    }
    public Double getPlaytimeHours(){
        return this.playtimeHours.getValue();
    }
    public Image getSplash(){
        return this.splash.getValue();
    }

    @Override
    public void writeExternal(ObjectOutput oo) throws IOException {
        oo.writeObject(this.name.getValue());
        oo.writeObject(this.description.getValue());
        oo.writeObject(this.path.getValue());
        oo.writeObject(this.uuid.getValue());
        oo.writeDouble(this.getPlaytimeMinutes());
        oo.writeDouble(this.getPlaytimeHours());
        oo.writeObject(this.splashUrl);
    }

    @Override
    public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
        this.name.setValue((String) oi.readObject());
        this.description.setValue((String) oi.readObject());
        this.path.setValue((String) oi.readObject());
        this.uuid.setValue((String) oi.readObject());
        double minutes = oi.readDouble();
        double hours = oi.readDouble();
        this.playtime = Duration.hours(hours).add(Duration.minutes(minutes));
        this.playtimeMinutes.setValue(this.playtime.toMinutes());
        this.playtimeHours.setValue(this.playtime.toHours());
        this.splashUrl = (String) oi.readObject();
        this.loadSplash();
    }
    
    @Override
    public String toString(){
        return this.name.getValue();
    }
}
