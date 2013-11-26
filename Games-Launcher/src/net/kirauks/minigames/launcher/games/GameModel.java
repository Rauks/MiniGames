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
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;

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
    
    public GameModel(){
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.path = new SimpleStringProperty();
        this.uuid = new SimpleStringProperty();
    }
    public GameModel(String name, String description, String url, String uuid){
        this();
        this.name.setValue(name);
        this.description.setValue(description);
        this.path.setValue(url);
        this.uuid.setValue(uuid);
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

    @Override
    public void writeExternal(ObjectOutput oo) throws IOException {
        oo.writeObject(this.name.getValue());
        oo.writeObject(this.description.getValue());
        oo.writeObject(this.path.getValue());
        oo.writeObject(this.uuid.getValue());
    }

    @Override
    public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
        this.name.setValue((String) oi.readObject());
        this.description.setValue((String) oi.readObject());
        this.path.setValue((String) oi.readObject());
        this.uuid.setValue((String) oi.readObject());
    }
    
    @Override
    public String toString(){
        return this.name.getValue();
    }
}
