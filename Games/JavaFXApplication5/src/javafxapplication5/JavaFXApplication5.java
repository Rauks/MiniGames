/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication5;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/**
 *
 * @author Laetitia
 */
public class JavaFXApplication5 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

       
         Scene scene = new Scene(root, 800, 500, Color.WHITE);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Hangman Start FXML !!! ");

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
