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
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import net.kirauks.minigames.launcher.games.GameModel;

/**
 *
 * @author Karl
 */
public class LauncherController implements Initializable {
    @FXML
    private Pane rootPane;
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
    private final SimpleStringProperty selectedGameUUID = new SimpleStringProperty(null);
    
    private FileChooser installChooser;
    
    @FXML
    private void handleMenuInstall(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Installation");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jeu", "*.gsetup"));
        
        File installer = chooser.showOpenDialog(this.getScene().getWindow());
        if(installer != null && installer.canRead()){
            try(BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(installer), Charset.forName("UTF-8")))){
                String titleStr = br.readLine();
                String pathStr = br.readLine();
                StringBuilder destStr = new StringBuilder();
                String descLine;
                while((descLine = br.readLine()) != null){
                    destStr.append(descLine).append(System.lineSeparator());
                }
                Path gameDatas = Paths.get(installer.getParent(), pathStr);
                if(!titleStr.isEmpty() && !pathStr.isEmpty() && Files.exists(gameDatas) && pathStr.endsWith(".jar")){
                    this.manager.installGameWithLocalCopy(new GameModel(titleStr, destStr.toString(), pathStr, UUID.randomUUID().toString()), gameDatas);
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
            this.manager.launchGame(new GameModel(this.selectedGameTitle.getValue(), this.selectedGameDescription.getValue(), this.selectedGamePath.getValue(), this.selectedGameUUID.getValue()));
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
                LauncherController.this.selectedGameUUID.unbind();
                if(newValue != null){
                    LauncherController.this.selectedGamePath.bind(newValue.pathProperty());
                    LauncherController.this.selectedGameTitle.bind(newValue.nameProperty());
                    LauncherController.this.selectedGameDescription.bind(newValue.descriptionProperty());
                    LauncherController.this.selectedGameUUID.bind(newValue.uuidProperty());
                }
            }
        });
    }
    
    protected Scene getScene(){
        return this.rootPane.getScene();
    }
}
