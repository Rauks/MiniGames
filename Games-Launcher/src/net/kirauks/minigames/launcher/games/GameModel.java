/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.launcher.games;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
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
    private SimpleStringProperty playtimeString;
    private Date lastplay;
    private SimpleStringProperty lastplayString;
    private String splashUrl;
    private SimpleObjectProperty<Image> splash;
    
    public GameModel(){
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.path = new SimpleStringProperty();
        this.uuid = new SimpleStringProperty();
        this.playtime = Duration.ZERO;
        this.playtimeString = new SimpleStringProperty();
        this.updatePlaytimeString();
        this.lastplay = null;
        this.lastplayString = new SimpleStringProperty();
        this.updateLastplayString();
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
            this.splash.setValue(new Image(new File(this.splashUrl).toURI().toString()));
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
    public ReadOnlyStringProperty playtimeStringProperty(){
        return this.playtimeString;
    }
    public ReadOnlyStringProperty lastplayStringProperty(){
        return this.lastplayString;
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
        this.updatePlaytimeString();
    }
    private void updatePlaytimeString(){
        StringBuilder sb = new StringBuilder();
        int minutes = ((int)this.playtime.toMinutes()) % 60;
        int hours = (int)this.playtime.toHours();
        
        if(hours != 0){
            sb.append(String.valueOf(hours));
            if(hours == 1){
                sb.append(" heure ");
            }
            else{
                sb.append(" heures ");
            }
            sb.append(String.format("%02d", minutes));
        }
        else{
            sb.append(String.valueOf(minutes));
        }
        
        if(minutes == 1){
            sb.append(" minute");
        }
        else{
            sb.append(" minutes");
        }
        
        this.playtimeString.setValue(sb.toString());
    }
    public void updateLastPlay(){
        this.lastplay = new Date();
        this.updateLastplayString();
    }
    private void updateLastplayString(){
        if(this.lastplay == null){
            this.lastplayString.setValue("Jamais");
        }
        else{
            Duration delta = Duration.millis((new Date()).getTime() - this.lastplay.getTime());
            if(delta.lessThan(Duration.hours(24))){
                StringBuilder sb = new StringBuilder();
                int minutes = ((int)delta.toMinutes()) % 60;
                int hours = (int)delta.toHours();

                if(hours != 0){
                    sb.append(String.valueOf(hours));
                    if(hours == 1){
                        sb.append(" heure ");
                    }
                    else{
                        sb.append(" heures ");
                    }
                    sb.append(String.format("%02d", minutes));
                }
                else{
                    sb.append(String.valueOf(minutes));
                }

                if(minutes == 1){
                    sb.append(" minute");
                }
                else{
                    sb.append(" minutes");
                }
                this.lastplayString.setValue("Il y a " + sb.toString());
            }
            else if(delta.lessThan(Duration.hours(48))){
                SimpleDateFormat hoursFormat = new SimpleDateFormat ("HH'h'mm");
                this.lastplayString.setValue("Hier à " + hoursFormat.format(this.lastplay));
            }
            else{
                SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/M/yyyy 'à' HH'h'mm");
                this.lastplayString.setValue("Le " + dateFormat.format(this.lastplay));
            }
        }
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
    public Duration getPlaytime(){
        return this.playtime;
    }
    public String getPlaytimeString(){
        return this.playtimeString.getValue();
    }
    public Date getLastplay(){
        return this.lastplay;
    }
    public String getLastplayString(){
        return this.lastplayString.getValue();
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
        oo.writeDouble(this.playtime.toMillis());
        oo.writeObject(this.lastplay);
        oo.writeObject(this.splashUrl);
    }

    @Override
    public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
        this.name.setValue((String) oi.readObject());
        this.description.setValue((String) oi.readObject());
        this.path.setValue((String) oi.readObject());
        this.uuid.setValue((String) oi.readObject());
        this.playtime = Duration.millis(oi.readDouble());
        this.updatePlaytimeString();
        this.lastplay = (Date) oi.readObject();
        this.updateLastplayString();
        this.splashUrl = (String) oi.readObject();
        this.loadSplash();
    }
    
    @Override
    public String toString(){
        return this.name.getValue();
    }
}
