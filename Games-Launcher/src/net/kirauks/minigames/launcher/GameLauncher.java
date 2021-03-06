/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Karl
 */
public class GameLauncher extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Launcher.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        LauncherController controller = fxmlLoader.<LauncherController>getController();
        controller.setStage(stage);
        
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.show();
        scene.getStylesheets().add(GameLauncher.class.getResource("style.css").toURI().toString());
        
        stage.setScene(scene);
        stage.setTitle("Games Launcher");
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
