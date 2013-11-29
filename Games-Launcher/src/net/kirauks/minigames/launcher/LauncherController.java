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
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import net.kirauks.javafx.dialog.Dialog;
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
    private Label gameLastTime;
    @FXML
    private ImageView gameSplash;
    @FXML
    private Button gameStart;
    @FXML
    private VBox gameInfoPane;
    
    @FXML
    private ListView listGames;
    
    private GameManager manager;
    private final ReadOnlyObjectWrapper<GameModel> selectedGame = new ReadOnlyObjectWrapper<>(null);
    
    
    @FXML
    private void handleMenuInstall(MouseEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Installation");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jeu", "*.gsetup"));
        
        File installer = chooser.showOpenDialog(this.getStage().getScene().getWindow());
        if(installer != null && installer.canRead()){
            try {
                this.manager.installGameWithLocalCopy(Paths.get(installer.toURI()));
                new Dialog("Installation terminée.", Dialog.DialogType.INFORMATION, Dialog.DialogOptions.OK, this.getStage()).showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
                new Dialog("Erreur d'installation.", Dialog.DialogType.ERROR, Dialog.DialogOptions.OK, this.getStage()).showAndWait();
            }
        }
    }
    @FXML
    private void handleMenuUninstall(MouseEvent event) {
        final GameModel game = this.selectedGame.getValue();
        new Dialog("Etes vous sur de vouloir désinstaller \"" + game.getName() + "\" ?", new Dialog.DialogListener() {
            @Override
            public void onResponse(Dialog.DialogResponse response) {
                if(response == Dialog.DialogResponse.YES){
                    try {
                        LauncherController.this.selectedGame.setValue(null);
                        LauncherController.this.manager.uninstallGameWithLocalRemove(game);
                    } catch (IOException ex) {
                        Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
                        new Dialog("Erreur de désinstallation.", Dialog.DialogType.ERROR, Dialog.DialogOptions.OK, LauncherController.this.getStage()).showAndWait();
                    }
                }
            }
        }, Dialog.DialogType.QUESTION, Dialog.DialogOptions.YES_NO, this.getStage()).showAndWait();
    }
    
    @FXML
    private void handleMenuGameStart(ActionEvent event) {
        try {
            this.manager.launchGame(this.selectedGame.getValue());
        } catch (IOException ex) {
            Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
            new Dialog("Erreur de lancement du jeu.", Dialog.DialogType.ERROR, Dialog.DialogOptions.OK, LauncherController.this.getStage()).showAndWait();
        }
    }
    
    private class GameListCell extends ListCell<GameModel>{
        private GameModel game;
        
        @Override
        public void updateItem(GameModel item, boolean empty) {
            super.updateItem(item, empty);
            this.game = item;
            this.setText(empty ? null : item.getName());
        }
        
        public GameModel getGame(){
            return this.game;
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
        this.listGames.setCellFactory(new Callback<ListView<GameModel>, ListCell<GameModel>>(){
            @Override
            public ListCell<GameModel> call(ListView<GameModel> p) {
                GameListCell cell = new GameListCell();
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent t) {
                        if(t.getClickCount() > 1){
                            GameListCell target = (GameListCell) t.getSource();
                            if(target.getGame() != null){
                                try {
                                    LauncherController.this.manager.launchGame(target.getGame());
                                } catch (IOException ex) {
                                    Logger.getLogger(LauncherController.class.getName()).log(Level.SEVERE, null, ex);
                                    new Dialog("Erreur de lancement du jeu.", Dialog.DialogType.ERROR, Dialog.DialogOptions.OK, LauncherController.this.getStage()).showAndWait();
                                }
                            }
                        }
                    }
                });
                return cell;
            }
        });
        
        this.gameInfoPane.visibleProperty().bind(this.selectedGame.isNotNull());
        
        final Glow glow = new Glow();
        glow.setLevel(0.0);
        this.gameStart.setEffect(glow);
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        final KeyValue kv = new KeyValue(glow.levelProperty(), 0.5);
        final KeyFrame kf = new KeyFrame(Duration.seconds(1.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();        
        
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
                LauncherController.this.gameLastTime.textProperty().unbind();
                LauncherController.this.gameSplash.imageProperty().unbind();
                if(newValue != null){
                    LauncherController.this.gameTitle.textProperty().bind(newValue.nameProperty());
                    LauncherController.this.gameDescription.textProperty().bind(newValue.descriptionProperty());
                    LauncherController.this.gameTime.textProperty().bind(newValue.playtimeStringProperty());
                    LauncherController.this.gameLastTime.textProperty().bind(newValue.lastplayStringProperty());
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
