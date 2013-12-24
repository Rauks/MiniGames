/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lg.hangman;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Laetitia
 */
public class DialogBox extends Stage {
    

    private Button btOK = new Button("OK");
    private Button btCancel = new Button("Cancel");
    private boolean abandonGame;

    public Button getBtOK() {
        return btOK;
    }

    public void setBtOK(Button btOK) {
        this.btOK = btOK;
    }

    public Button getBtCancel() {
        return btCancel;
    }

    public void setBtCancel(Button btCancel) {
        this.btCancel = btCancel;
    }

    public boolean isAbandonGame() {
        return abandonGame;
    }

    public void setAbandonGame(boolean abandonGame) {
        this.abandonGame = abandonGame;
    }

    public DialogBox(String msg) {

        this.initModality(Modality.APPLICATION_MODAL);
       //this.initModality(Modality.WINDOW_MODAL);
        //this.initStyle(StageStyle.UTILITY);
                this.setOnCloseRequest(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
              System.out.println("Stage is closing");
              setAbandonGame(false);
          }
      }); 
        this.sizeToScene();
        this.setScene(new Scene(VBoxBuilder.create().children(new Text(msg), btOK, btCancel).
                alignment(Pos.CENTER).padding(new Insets(5)).build()));

        btOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Abandon oui");
                setAbandonGame(true);
                close();
            }
        });

        btCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("abandon non");
                setAbandonGame(false);
                close();
            }
        });
        this.showAndWait();
        
    }
}

