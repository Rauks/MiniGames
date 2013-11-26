/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.launcher;

import java.io.IOException;
import net.kirauks.minigames.launcher.games.GameManager;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import net.kirauks.minigames.launcher.games.GameModel;

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
    private Button gameStart;
    
    @FXML
    private ListView listGames;
    
    private GameManager manager;
    private final SimpleStringProperty selectedGamePath = new SimpleStringProperty(null);
    private final SimpleStringProperty selectedGameTitle = new SimpleStringProperty(null);
    private final SimpleStringProperty selectedGameDescription = new SimpleStringProperty(null);
    
    @FXML
    private void handleMenuInstall(ActionEvent event) {
        try {
            this.manager.installGame(new GameModel("Test", "Test description", "test.txt"));
        } catch (IOException ex) {
            Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void handleMenuUninstall(ActionEvent event) {
        
    }
    
    @FXML
    private void handleMenuGameStart(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.manager = GameManager.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.listGames.setItems(this.manager.gamesListProperty());
        
        this.gameTitle.disableProperty().bind(this.listGames.getSelectionModel().selectedItemProperty().isNull());
        this.gameDescription.disableProperty().bind(this.listGames.getSelectionModel().selectedItemProperty().isNull());
        this.gameStart.disableProperty().bind(this.listGames.getSelectionModel().selectedItemProperty().isNull());
        
        this.gameTitle.visibleProperty().bind(this.listGames.getSelectionModel().selectedItemProperty().isNotNull());
        this.gameDescription.visibleProperty().bind(this.listGames.getSelectionModel().selectedItemProperty().isNotNull());
        this.gameStart.visibleProperty().bind(this.listGames.getSelectionModel().selectedItemProperty().isNotNull());
        
        this.gameTitle.textProperty().bind(this.selectedGameTitle);
        this.gameDescription.textProperty().bind(this.selectedGameDescription);
        
        this.listGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GameModel>(){
            @Override
            public void changed(ObservableValue<? extends GameModel> observableValue, GameModel oldValue, GameModel newValue) {
                LauncherController.this.selectedGamePath.unbind();
                LauncherController.this.selectedGameTitle.unbind();
                LauncherController.this.selectedGameDescription.unbind();
                if(newValue != null){
                    LauncherController.this.selectedGamePath.bind(newValue.pathProperty());
                    LauncherController.this.selectedGameTitle.bind(newValue.nameProperty());
                    LauncherController.this.selectedGameDescription.bind(newValue.descriptionProperty());
                }
            }
        });
    }
}
