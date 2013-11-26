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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
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
    private SimpleStringProperty gameToLaunchPath = new SimpleStringProperty(null);
    
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
        
        this.gameDescription.disableProperty().bind(this.listGames.getSelectionModel().selectedItemProperty().isNull());
        this.gameTitle.disableProperty().bind(this.listGames.getSelectionModel().selectedItemProperty().isNull());
        this.gameStart.disableProperty().bind(this.listGames.getSelectionModel().selectedItemProperty().isNull());
        
        this.listGames.selectionModelProperty().addListener(new ChangeListener<GameModel>(){
            @Override
            public void changed(ObservableValue<? extends GameModel> observableValue, GameModel oldValue, GameModel newValue) {
                LauncherController.this.gameToLaunchPath.unbind();
                LauncherController.this.gameTitle.textProperty().unbind();
                LauncherController.this.gameDescription.textProperty().unbind();
                if(newValue != null){
                    LauncherController.this.gameToLaunchPath.bind(newValue.pathProperty());
                    LauncherController.this.gameTitle.textProperty().bind(newValue.nameProperty());
                    LauncherController.this.gameDescription.textProperty().bind(newValue.descriptionProperty());
                }
            }
        });
    }
}
