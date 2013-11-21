/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.test;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import net.kirauks.minigames.engine.GameApplication;

/**
 *
 * @author Karl
 */
public class GameTest extends GameApplication {
    @Override
    public String createStageTitle() {
        return "Test Game";
    }

    @Override
    public Scene createScene() {
        Label l = new Label("Test");
        l.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent t) {
                System.out.println("Mouse clicked on Label");
            }
        });
        return new Scene(l);
    }

    @Override
    public void onCloseStage() {
        System.out.println("Close Call");
    }

    @Override
    public void onPauseGame() {
        System.out.println("Pause Call");
    }

    @Override
    public void onResumeGame() {
        System.out.println("Resume Call");
    }

    @Override
    public void onActionKeyPressed() {
        System.out.println("Action Pressed Call");
    }

    @Override
    public void onActionKeyReleased() {
        System.out.println("Action Released Call");
    }

    @Override
    public void onUpKeyPressed() {
        System.out.println("Up Pressed Call");
    }

    @Override
    public void onDownKeyPressed() {
        System.out.println("Down Pressed Call");
    }

    @Override
    public void onLeftKeyPressed() {
        System.out.println("Left Pressed Call");
    }

    @Override
    public void onRightKeyPressed() {
        System.out.println("Right Pressed Call");
    }
}
