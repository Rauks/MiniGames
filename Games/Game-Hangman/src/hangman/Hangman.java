/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Laetitia
 */
public class Hangman extends Application {

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();

        Scene scene = new Scene(root, 800, 600, Color.WHITE);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Hangman Start");
        
        
       
        ViewScene v = new ViewScene();
        v.setLayoutX(0);
        v.setLayoutY(0);
        

        
        
        root.getChildren().add(v);
        

        
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
        Application.launch(Hangman.class, args);
    }

}
