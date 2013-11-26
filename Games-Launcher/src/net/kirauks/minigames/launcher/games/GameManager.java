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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private final static Path LOCAL_GAMES_REPOSITORY = FileSystems.getDefault().getPath("games");
    private final static Path DATABASE_FILE = FileSystems.getDefault().getPath("games.db");
    private final static Path DATABASE_FILE_BACKUP = FileSystems.getDefault().getPath("games.db.bak");
    private final static File DB = DATABASE_FILE.toFile();
    private final static File DB_BACKUP = DATABASE_FILE_BACKUP.toFile();
    
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
        if(!Files.isDirectory(LOCAL_GAMES_REPOSITORY)){
            Files.createDirectory(LOCAL_GAMES_REPOSITORY);
        }
        Path destPath = FileSystems.getDefault().getPath(LOCAL_GAMES_REPOSITORY.toString(), UUID.randomUUID().toString().concat(".jar"));
        System.out.println("Copy to " + destPath);
        Files.copy(gameDatas.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Copied");
        this.installGame(new GameModel(game.getName(), game.getDescription(), destPath.toString()));
    }
    public void uninstallGame(GameModel game) throws IOException{
        this.games.remove(game);
        this.writeDatabase(new ArrayList<>(this.games));
    }
    
    
    private synchronized ArrayList<GameModel> readDatabase() throws IOException{
        ArrayList<GameModel> datas = null;
        try(ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(DB)))){
            datas = (ArrayList<GameModel>) ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }
    private synchronized void writeDatabase(ArrayList<GameModel> datas) throws IOException{
        Files.copy(DB.toPath(), DB_BACKUP.toPath(), StandardCopyOption.REPLACE_EXISTING);
        try (ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(DB)))) {
            oos.writeObject(datas);
            oos.flush();
        }
    }
}
