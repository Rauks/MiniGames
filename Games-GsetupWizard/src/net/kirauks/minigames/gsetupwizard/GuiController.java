/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.gsetupwizard;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.kirauks.minigames.gsetupwizard.tasks.GsetupTask;

/**
 *
 * @author Karl
 */
public class GuiController implements Initializable {
    @FXML
    private TextField fileField;
    @FXML
    private TextField splashField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button openButton;
    @FXML
    private Button openSplashButton;
    @FXML
    private Button genButton;
    @FXML
    private ProgressIndicator progress;
    
    private final SimpleBooleanProperty building = new SimpleBooleanProperty(false);
    private final ReadOnlyObjectWrapper<File> gameFile = new ReadOnlyObjectWrapper<>(null);
    private final ReadOnlyObjectWrapper<File> splashFile = new ReadOnlyObjectWrapper<>(null);
    
    @FXML
    private void handleButtonFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choix du jeu");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jeu", "*.jar"));
        
        File gameJar = chooser.showOpenDialog(this.getStage().getScene().getWindow());
        if(gameJar != null && gameJar.canRead()){
            this.gameFile.setValue(gameJar);
        }
    }
    @FXML
    private void handleButtonSplashFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choix de l'image");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.jpg"));
        
        File gameJar = chooser.showOpenDialog(this.getStage().getScene().getWindow());
        if(gameJar != null && gameJar.canRead()){
            this.splashFile.setValue(gameJar);
        }
    }
    @FXML
    private void handleButtonBuild(ActionEvent event) {
        Task<Void> gsetupBuild = new GsetupTask(this.titleField.getText(), this.descriptionArea.getText(), this.gameFile.getValue(), this.splashFile.getValue());
        this.building.setValue(true);
        gsetupBuild.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                GuiController.this.building.setValue(false);
            }
        });
        gsetupBuild.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                GuiController.this.building.setValue(false);
            }
        });
        new Thread(gsetupBuild).start();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.titleField.disableProperty().bind(this.fileField.textProperty().isEqualTo("").or(
                                              this.building));
        this.descriptionArea.disableProperty().bind(this.fileField.textProperty().isEqualTo("").or(
                                              this.building));
        this.openButton.disableProperty().bind(this.building);
        this.openSplashButton.disableProperty().bind(this.building);
        this.genButton.disableProperty().bind(this.fileField.textProperty().isEqualTo("").or(
                                              this.splashField.textProperty().isEqualTo("").or(
                                              this.titleField.textProperty().isEqualTo("").or(
                                              this.descriptionArea.textProperty().isEqualTo("").or(
                                              this.building)))));
        this.progress.visibleProperty().bind(this.building);
        
        this.gameFile.addListener(new ChangeListener<File>() {
            @Override
            public void changed(ObservableValue<? extends File> ov, File oldFile, File newFile) {
                GuiController.this.fileField.setText(null);
                if(newFile != null){
                    GuiController.this.fileField.textProperty().set(newFile.getAbsolutePath());
                }
            }
        });
        this.splashFile.addListener(new ChangeListener<File>() {
            @Override
            public void changed(ObservableValue<? extends File> ov, File oldFile, File newFile) {
                GuiController.this.splashField.setText(null);
                if(newFile != null){
                    GuiController.this.splashField.textProperty().set(newFile.getAbsolutePath());
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
