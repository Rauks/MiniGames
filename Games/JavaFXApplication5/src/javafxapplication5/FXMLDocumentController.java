/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javafxapplication5;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Laetitia
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label textLabelChoose;
    
    @FXML
    private void handleButtonActionLevel1(ActionEvent event) {
        System.out.println("You clicked level 1!");
        textLabelChoose.setText("Level 1 is choosen");
    }
    
    @FXML
    private void handleButtonActionLevel2(ActionEvent event) {
        System.out.println("You clicked me 2 !");
        textLabelChoose.setText("Level 2 is choosen");
    }
        @FXML
    private void handleButtonActionLevel3(ActionEvent event) {
        System.out.println("You clicked me 3 !");
        textLabelChoose.setText("Level 3 is choosen");
    }
    
          @FXML
    private void test(MouseEvent event) {
        
        System.out.println("You clicked me 3 !");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
