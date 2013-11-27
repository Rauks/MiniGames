/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.gsetupwizard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Karl
 */
public class GuiController implements Initializable {
    
    @FXML
    private TextField fileField;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button genButton;
    
    private ReadOnlyObjectWrapper<File> gameFile = new ReadOnlyObjectWrapper<>(null);
    
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
    private void handleButtonBuild(ActionEvent event) {
        Path gamePath = Paths.get(this.gameFile.getValue().getPath());
        String fileName = gamePath.getFileName().toString();
        Path gsetupPath = Paths.get(gamePath.getParent().toString(), fileName.substring(0, fileName.lastIndexOf(".")).concat(".gsetup"));
        
        try {
            Files.deleteIfExists(gsetupPath);
            Files.createFile(gsetupPath);
        } catch (IOException ex) {
            Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(gsetupPath.toString())))){
            bw.write(this.titleField.getText());
            bw.write(System.lineSeparator());
            bw.write(gamePath.getFileName().toString());
            bw.write(System.lineSeparator());
            bw.write(this.descriptionArea.getText());
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.titleField.disableProperty().bind(this.fileField.textProperty().isEqualTo(""));
        this.descriptionArea.disableProperty().bind(this.fileField.textProperty().isEqualTo(""));
        this.genButton.disableProperty().bind(this.fileField.textProperty().isEqualTo("").or(
                                              this.titleField.textProperty().isEqualTo("").or(
                                              this.descriptionArea.textProperty().isEqualTo(""))));
        
        this.gameFile.addListener(new ChangeListener<File>() {
            @Override
            public void changed(ObservableValue<? extends File> ov, File oldFile, File newFile) {
                GuiController.this.fileField.setText(null);
                if(newFile != null){
                    GuiController.this.fileField.textProperty().set(newFile.getAbsolutePath());
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
