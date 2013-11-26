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
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.kirauks.minigames.launcher.games.utils.LibCopyVisitor;

/**
 *
 * @author Karl
 */
public class GameManager {

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
            this.writeDatabase(new ArrayList<GameModel>());
        }
        this.games.setAll(this.readDatabase());
    }

    public ObservableList<GameModel> gamesListProperty() {
        return this.games;
    }

    public void launchGame(final GameModel game) throws IOException {
        if (!Files.isDirectory(GAME_RUN_DIRECTORY)) {
            Files.createDirectory(GAME_RUN_DIRECTORY);
        }

        Path gameFile = FileSystems.getDefault().getPath(game.getPath());

        Path runPath = FileSystems.getDefault().getPath(GAME_RUN_DIRECTORY.toString(), game.getUuid());
        if (!Files.isDirectory(runPath)) {
            Files.createDirectory(runPath);
        }

        final ProcessBuilder pb = new ProcessBuilder("java", "-Xms1024m", "-Xmx1024m", "-jar", gameFile.toAbsolutePath().toString());
        pb.directory(runPath.toAbsolutePath().toFile());
        if(this.startListener != null){
            this.startListener.onGameStart(game);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process p = pb.start();
                    p.waitFor();
                    if(GameManager.this.finishListener != null){
                        GameManager.this.finishListener.onGameFinish(game);
                    }
                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

    }

    public void installGame(GameModel game) throws IOException {
        this.games.add(game);
        this.writeDatabase(new ArrayList<>(this.games));
    }

    public void installGameWithLocalCopy(GameModel game, Path gameDatas) throws IOException {
        if (!Files.isDirectory(LOCAL_GAMES_REPOSITORY)) {
            Files.createDirectory(LOCAL_GAMES_REPOSITORY);
        }

        String gameUUID = game.getUuid();
        Path installPath = Paths.get(LOCAL_GAMES_REPOSITORY.toString(), gameUUID.toString());
        Path gamePath = Paths.get(installPath.toString(), "game.jar");
        Path libsPath = Paths.get(installPath.toString(), "lib");

        if (!Files.isDirectory(installPath)) {
            Files.createDirectory(installPath);
        }

        Files.copy(gameDatas, gamePath, StandardCopyOption.REPLACE_EXISTING);

        Path libDatas = Paths.get(gameDatas.getParent().toString(), "lib");
        Files.walkFileTree(libDatas, new LibCopyVisitor(libDatas, libsPath, StandardCopyOption.REPLACE_EXISTING));

        game.setPath(gamePath.toString());
        this.installGame(game);
    }

    public void uninstallGame(GameModel game) throws IOException {
        this.games.remove(game);
        this.writeDatabase(new ArrayList<>(this.games));
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

    private synchronized void writeDatabase(ArrayList<GameModel> datas) throws IOException {
        Files.copy(DB.toPath(), DB_BACKUP.toPath(), StandardCopyOption.REPLACE_EXISTING);
        try (ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(DB)))) {
            oos.writeObject(datas);
            oos.flush();
        }
    }
}
