/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package games.gsetupwizard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private void handleButtonFile(ActionEvent event) {
        
    }
    @FXML
    private void handleButtonBuild(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
