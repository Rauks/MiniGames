/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import net.kirauks.minigames.launcher.games.GameManager;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.kirauks.minigames.launcher.games.GameModel;
import net.kirauks.minigames.launcher.games.OnGameFinishListener;
import net.kirauks.minigames.launcher.games.OnGameStartListener;

/**
 *
 * @author Karl
 */
public class LauncherController implements Initializable {
    @FXML
    private TextArea gameDescription;
    @FXML
    private Label gameTitle;
    @FXML
    private Label gameTime;
    @FXML
    private ImageView gameSplash;
    @FXML
    private Button gameStart;
    @FXML
    private ScrollPane gameInfoPane;
    
    @FXML
    private ListView listGames;
    
    private GameManager manager;
    private final ReadOnlyObjectWrapper<GameModel> selectedGame = new ReadOnlyObjectWrapper<>(null);
    
    
    @FXML
    private void handleMenuInstall(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Installation");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jeu", "*.gsetup"));
        
        File installer = chooser.showOpenDialog(this.getStage().getScene().getWindow());
        if(installer != null && installer.canRead()){
            try(BufferedReader br =  new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(installer)), Charset.forName("UTF-8")))){
                String titleStr = br.readLine();
                String pathStr = br.readLine();
                String splashStr = br.readLine();
                StringBuilder destStr = new StringBuilder();
                String descLine;
                while((descLine = br.readLine()) != null){
                    destStr.append(descLine).append(System.lineSeparator());
                }
                Path gameDatas = Paths.get(installer.getParent(), pathStr);
                if(!titleStr.isEmpty() && !pathStr.isEmpty() && !splashStr.isEmpty() && Files.exists(gameDatas) && pathStr.endsWith(".jar")){
                    this.manager.installGameWithLocalCopy(new GameModel(titleStr, destStr.toString(), pathStr, splashStr, UUID.randomUUID().toString()), gameDatas);
                }
            } catch(IOException ex){
                 Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @FXML
    private void handleMenuUninstall(ActionEvent event) {
        
    }
    
    @FXML
    private void handleMenuGameStart(ActionEvent event) {
        try {
            this.manager.launchGame(this.selectedGame.getValue());
        } catch (IOException ex) {
            Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.manager = GameManager.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.manager.setOnGameStartListener(new OnGameStartListener() {
            @Override
            public void onGameStart(GameModel game) {
                LauncherController.this.getStage().setIconified(true);
            }
        });
        this.manager.setOnGameFinishListener(new OnGameFinishListener() {
            @Override
            public void onGameFinish(GameModel game) {
                LauncherController.this.getStage().setIconified(false);
            }
        });
        
        this.listGames.setItems(this.manager.gamesListProperty());
        
        this.gameInfoPane.visibleProperty().bind(this.selectedGame.isNotNull());
        
        this.listGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GameModel>(){
            @Override
            public void changed(ObservableValue<? extends GameModel> observableValue, GameModel oldValue, GameModel newValue) {
                LauncherController.this.selectedGame.setValue(null);
                if(newValue != null){
                    LauncherController.this.selectedGame.setValue(newValue);
                }
            }
        });
        
        this.selectedGame.addListener(new ChangeListener<GameModel>() {
            @Override
            public void changed(ObservableValue<? extends GameModel> observableValue, GameModel oldValue, GameModel newValue) {
                LauncherController.this.gameTitle.textProperty().unbind();
                LauncherController.this.gameDescription.textProperty().unbind();
                LauncherController.this.gameTime.textProperty().unbind();
                LauncherController.this.gameSplash.imageProperty().unbind();
                if(newValue != null){
                    LauncherController.this.gameTitle.textProperty().bind(newValue.nameProperty());
                    LauncherController.this.gameDescription.textProperty().bind(newValue.descriptionProperty());
                    LauncherController.this.gameTime.textProperty().bind(newValue.playtimeHoursProperty().asString()
                                                                        .concat("h")
                                                                        .concat(newValue.playtimeMinutesProperty().asString()));
                    LauncherController.this.gameSplash.imageProperty().bind(newValue.splashProperty());
                }
            }
        });
    }
    
    private Stage stage;
    protected Stage getStage(){
        return this.stage;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
}
