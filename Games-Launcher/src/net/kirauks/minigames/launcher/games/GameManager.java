/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.launcher.games;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Karl
 */
public class GameManager {
    private final static String LOCAL_GAMES_REPOSITORY = "games";
    private final static String DATABASE_FILE = "games.db";
    private final static String DATABASE_FILE_BACKUP = "games.db.bak";
    private final static File DB = new File(DATABASE_FILE);
    private final static File DB_BACKUP = new File(DATABASE_FILE_BACKUP);
    
    private static GameManager INSTANCE = null;
    
    public static GameManager getInstance() throws IOException{
        if(INSTANCE == null){
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }
    
    private final ObservableList<GameModel> games = FXCollections.observableArrayList();
    
    public GameManager() throws IOException{
        if(!DB.exists()){
            DB.createNewFile();
            this.writeDatabase(new ArrayList<GameModel>());
        }
        this.games.setAll(this.readDatabase());
    }
    
    public ObservableList<GameModel> gamesListProperty(){
        return this.games;
    }
    
    public void installGame(GameModel game) throws IOException{
        this.games.add(game);
        this.writeDatabase(new ArrayList<>(this.games));
    }
    public void installGameWithLocalCopy(GameModel game, File gameDatas) throws IOException{
        File dest = new File(LOCAL_GAMES_REPOSITORY + File.pathSeparator + UUID.randomUUID().toString());
        Files.copy(gameDatas.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        this.installGame(new GameModel(game.getName(), game.getDescription(), dest.getPath()));
    }
    public void uninstallGame(GameModel game) throws IOException{
        this.games.remove(game);
        this.writeDatabase(new ArrayList<>(this.games));
    }
    
    
    private ArrayList<GameModel> readDatabase() throws IOException{
        ArrayList<GameModel> datas = null;
        try(ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(DB)))){
            datas = (ArrayList<GameModel>) ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }
    private void writeDatabase(ArrayList<GameModel> datas) throws IOException{
        Files.copy(DB.toPath(), DB_BACKUP.toPath(), StandardCopyOption.REPLACE_EXISTING);
        
        ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(DB)));
        oos.writeObject(datas);
    }
}
