/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.launcher;

import java.io.File;
import java.io.IOException;
import net.kirauks.minigames.launcher.games.GameManager;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
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
    private MenuItem menuUninstall;
    
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
            try {
                this.manager.installGameWithLocalCopy(Paths.get(installer.toURI()));
            } catch (IOException ex) {
                Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @FXML
    private void handleMenuUninstall(ActionEvent event) {
        GameModel game = this.selectedGame.getValue();
        this.selectedGame.setValue(null);
        try {
            this.manager.uninstallGameWithLocalRemove(game);
        } catch (IOException ex) {
            Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        this.menuUninstall.disableProperty().bind(this.selectedGame.isNull());
        
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
                    LauncherController.this.gameTime.textProperty().bind(newValue.playtimeStringProperty());
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
