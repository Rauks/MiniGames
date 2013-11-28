/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.kirauks.minigames.launcher.games;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.util.Duration;
import net.kirauks.minigames.launcher.games.tasks.LaunchTask;
import net.kirauks.minigames.launcher.games.utils.DeleteVisitor;

/**
 *
 * @author Karl
 */
public class GameManager {
    private final static Path GAME_SPLASH_FILE = Paths.get("game.splash");
    private final static Path GAME_METADATA_FILE = Paths.get("game.metadata");
    private final static Path GAME_RUN_FILE = Paths.get("game.jar");
    private final static Path GAME_RUN_DIRECTORY = Paths.get("games-data");
    private final static Path LOCAL_GAMES_REPOSITORY = Paths.get("games");
    private final static Path DATABASE_FILE = Paths.get("games.db");
    private final static Path DATABASE_FILE_BACKUP = Paths.get("games.db.bak");
    private final static File DB = DATABASE_FILE.toFile();
    private final static File DB_BACKUP = DATABASE_FILE_BACKUP.toFile();

    private OnGameStartListener startListener = null;
    private OnGameFinishListener finishListener = null;

    public void setOnGameStartListener(OnGameStartListener listener) {
        this.startListener = listener;
    }

    public void setOnGameFinishListener(OnGameFinishListener listener) {
        this.finishListener = listener;
    }

    private static GameManager INSTANCE = null;

    public static GameManager getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    private final ObservableList<GameModel> games = FXCollections.observableArrayList();

    public GameManager() throws IOException {
        if (!DB.exists()) {
            DB.createNewFile();
            this.writeDatabase();
        }
        this.games.setAll(this.readDatabase());
    }

    public ObservableList<GameModel> gamesListProperty() {
        return this.games;
    }

    public void launchGame(final GameModel game) throws IOException {
        Path gameFile = FileSystems.getDefault().getPath(game.getPath());
        Path runPath = FileSystems.getDefault().getPath(GAME_RUN_DIRECTORY.toString(), game.getUuid());
        Files.createDirectories(runPath);

        final ProcessBuilder pb = new ProcessBuilder("java", "-Xms1024m", "-Xmx1024m", "-jar", gameFile.toAbsolutePath().toString());
        pb.directory(runPath.toAbsolutePath().toFile());

        Task launcher = new LaunchTask(pb);
        final long startTime = System.currentTimeMillis();
        launcher.setOnRunning(new EventHandler() {
            @Override
            public void handle(Event t) {
                if(GameManager.this.startListener != null){
                    GameManager.this.startListener.onGameStart(game);
                }
            }
        });
        launcher.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event t) {
                if(GameManager.this.finishListener != null){
                    game.addToPlaytime(Duration.millis(System.currentTimeMillis() - startTime));
                    try {
                        GameManager.this.writeDatabase();
                    } catch (IOException ex) {
                        Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    GameManager.this.finishListener.onGameFinish(game);
                }
            }
        });
        launcher.setOnFailed(new EventHandler() {
            @Override
            public void handle(Event t) {
                if(GameManager.this.finishListener != null){
                    game.addToPlaytime(Duration.millis(System.currentTimeMillis() - startTime));
                    try {
                        GameManager.this.writeDatabase();
                    } catch (IOException ex) {
                        Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    GameManager.this.finishListener.onGameFinish(game);
                }
            }
        });
        
        new Thread(launcher).start();

    }

    public void installGame(GameModel game) throws IOException {
        this.games.add(game);
        this.writeDatabase();
    }

    public void installGameWithLocalCopy(Path gsetupFile) throws IOException{
        String gameUUID = UUID.randomUUID().toString();
        Path installPath = Paths.get(LOCAL_GAMES_REPOSITORY.toString(), gameUUID);
        Files.createDirectories(installPath);
        
        try(ZipFile gsetup = new ZipFile(gsetupFile.toFile())){
            Enumeration<? extends ZipEntry> gsetupEntries = gsetup.entries();
            while(gsetupEntries.hasMoreElements()){
                ZipEntry gsetupEntry = gsetupEntries.nextElement();
                Path toDeflate = Paths.get(installPath.toString(), gsetupEntry.getName());
                if(Files.isDirectory(toDeflate)) {
                    Files.createDirectory(toDeflate);
                }
                else{
                    Files.createDirectories(toDeflate.getParent());
                    final int BUFFER = 2048;
                    byte data[] = new byte[BUFFER];
                    try(OutputStream os = new BufferedOutputStream(new FileOutputStream(toDeflate.toFile()), BUFFER);
                      InputStream is = gsetup.getInputStream(gsetupEntry)){
                        int read = -1;
                        while((read = is.read(data, 0, BUFFER)) != -1){
                            os.write(data, 0, read);
                        }
                        os.flush();
                    }
                }
            } 
            
            Path metaDatas = Paths.get(installPath.toString(), GAME_METADATA_FILE.toString());
            if(!Files.isRegularFile(metaDatas)){
                throw new IOException("Unreachable game metadatas.");
            }
            Path gamePath = Paths.get(installPath.toString(), GAME_RUN_FILE.toString());
            if(!Files.isRegularFile(gamePath)){
                throw new IOException("Unreachable game executable.");
            }
            Path splashPath = Paths.get(installPath.toString(), GAME_SPLASH_FILE.toString());
            if(!Files.isRegularFile(splashPath)){
                throw new IOException("Unreachable game splash.");
            }
            
            String titleStr;
            StringBuilder descStr = new StringBuilder();
            try(BufferedReader br =  new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(metaDatas.toFile())), Charset.forName("UTF-8")))){
                titleStr = br.readLine();
                String descLine;
                while((descLine = br.readLine()) != null){
                    descStr.append(descLine).append(System.lineSeparator());
                }
            }
            
            Files.deleteIfExists(metaDatas);
            this.installGame(new GameModel(titleStr, descStr.toString(), gamePath.toString(), splashPath.toString(), gameUUID));
            
        } catch (IOException ex) {
            Files.walkFileTree(installPath, new DeleteVisitor());
            throw ex;
        }
    }

    public void uninstallGame(GameModel game) throws IOException {
        this.games.remove(game);
        this.writeDatabase();
    }
    public void uninstallGameWithLocalRemove(GameModel game) throws IOException {
        Path installPath = Paths.get(LOCAL_GAMES_REPOSITORY.toString(), game.getUuid());
        Path sandboxPath = Paths.get(GAME_RUN_DIRECTORY.toString(), game.getUuid());
        
        if(Files.exists(installPath)){
            Files.walkFileTree(installPath, new DeleteVisitor());
        }
        if(Files.exists(sandboxPath)){
            Files.walkFileTree(sandboxPath, new DeleteVisitor());
        }
    
        this.uninstallGame(game);
    }

    private synchronized ArrayList<GameModel> readDatabase() throws IOException {
        ArrayList<GameModel> datas = null;
        try (ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(DB)))) {
            datas = (ArrayList<GameModel>) ois.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datas;
    }

    private synchronized void writeDatabase() throws IOException {
        ArrayList<GameModel> datas = new ArrayList<>(this.games);
        Files.copy(DB.toPath(), DB_BACKUP.toPath(), StandardCopyOption.REPLACE_EXISTING);
        try (ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(DB)))) {
            oos.writeObject(datas);
            oos.flush();
        }
    }
}
