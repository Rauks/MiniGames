/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.kirauks.laetitia.hangman;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import net.kirauks.javafx.dialog.Dialog;
import net.kirauks.javafx.dialog.Dialog.DialogListener;
import net.kirauks.javafx.dialog.Dialog.DialogOptions;
import net.kirauks.javafx.dialog.Dialog.DialogResponse;
import net.kirauks.javafx.dialog.Dialog.DialogType;
import net.kirauks.laetitia.hangman.model.AlgoHangManController;

/**
 *
 * @author Laetitia
 */
public final class GameController implements Initializable {

    private static final Color TOUCH_BACKGROUND = Color.WHITE;

    private int currentLevel = 1;
    private char letterSelected;
    private AlgoHangManController levelController;

    @FXML
    private Label textLabelChoose, helpStringLabel, wordGuess, ResultWinLoseLabel, count, letterSelectedLabelString;
    @FXML
    private Button level1, level2, level3;

    @FXML
    private Circle head;
    @FXML
    private Line gallows_bottom, gallows_top, gallows_middle, gallows_head, torso, armLeft, armRight, legLeft, legRight;

    @FXML
    private Rectangle A, Z, E, R, T, Y, U, I, O, P, Q, S, D, F, G, H, J, K, L, M, W, X, C, V, B, N;

    private final ArrayList<Rectangle> touch = new ArrayList<>();

    private void fillTouchWhite() {
        for (Rectangle t : touch) {
            t.setFill(TOUCH_BACKGROUND);
        }
    }

    private void fillTouchs() {
        this.touch.add(A);
        this.touch.add(Z);
        this.touch.add(E);
        this.touch.add(R);
        this.touch.add(T);
        this.touch.add(Y);
        this.touch.add(U);
        this.touch.add(I);
        this.touch.add(O);
        this.touch.add(P);
        this.touch.add(Q);
        this.touch.add(S);
        this.touch.add(D);
        this.touch.add(F);
        this.touch.add(G);
        this.touch.add(H);
        this.touch.add(J);
        this.touch.add(K);
        this.touch.add(L);
        this.touch.add(M);
        this.touch.add(W);
        this.touch.add(X);
        this.touch.add(C);
        this.touch.add(V);
        this.touch.add(B);
        this.touch.add(N);
    }

    @FXML
    private void handleButtonActionLevel1(ActionEvent event) {
        this.currentLevel = 1;
        this.startGame(this.currentLevel);
    }

    @FXML
    private void handleButtonActionLevel2(ActionEvent event) {
        this.currentLevel = 2;
        this.startGame(this.currentLevel);
    }

    @FXML
    private void handleButtonActionLevel3(ActionEvent event) {
        this.currentLevel = 3;
        this.startGame(this.currentLevel);
    }

    @FXML
    private void handleButtonActionAbandon(ActionEvent event) {
        new Dialog("Are you sure you want to give up ?", new DialogListener() {
            @Override
            public void onResponse(DialogResponse response) {
                if (response == DialogResponse.YES) {
                    helpStringLabel.setText("The word was : " + levelController.getWord());
                    startGame(currentLevel);
                }
            }
        }, DialogType.QUESTION, DialogOptions.YES_NO, this.getStage()).show();
    }

    @FXML
    private void handleButtonActionHelp(ActionEvent event) {
        helpStringLabel.setText("The word was : " + levelController.getWord());
    }

    @FXML
    private void releaseTouch(MouseEvent event) {
        Rectangle r = (Rectangle) event.getSource();
        r.setFill(Color.LIGHTGRAY);
        r.setTranslateY(0);
    }

    @FXML
    private void pressTouch(MouseEvent event) {
        final Rectangle r = (Rectangle) event.getSource();
        System.out.println("r id :" + r.getId());
        String myIdLetter = r.getId();
        r.setFill(Color.DARKGREY);
        r.setTranslateY(-3);
        letterSelected = r.getId().toLowerCase().charAt(0);
        System.out.println("DrawScene letterSelected :" + letterSelected);
        letterSelectedLabelString.setText(Character.toString(letterSelected).toUpperCase());
        levelController.Guess(letterSelected);

        //updating of word to guess
        wordGuess.setText(levelController.getGuess_wordChartoString());

        //updating to counter
        count.setText(Integer.toString(levelController.getCount()));

        //draw man if wrong letter
        if (levelController.isDraw()) {
            manDrawOneByOne(levelController.getCount());
        }
        ResultWinLoseLabel.setText(Integer.toString(levelController.getMax_choose() - levelController.getCount()));
        if (levelController.getCount() == levelController.getMax_choose()) {
            r.setTranslateY(0);

            new Dialog("Game Over !", new DialogListener() {
                @Override
                public void onResponse(DialogResponse response) {
                    new Dialog("Play Again ?", new DialogListener() {
                        @Override
                        public void onResponse(DialogResponse response) {
                            if (response == DialogResponse.YES) {
                                startGame(currentLevel);
                            } else {
                                fillTouchWhite();
                            }
                        }
                    }, DialogType.QUESTION, DialogOptions.YES_NO, GameController.this.getStage()).show();
                }
            }, DialogType.WARNING, DialogOptions.OK, this.getStage()).show();
        }
        if (levelController.isWin()) {
            r.setTranslateY(0);

            new Dialog("Epic Win !", new DialogListener() {
                @Override
                public void onResponse(DialogResponse response) {
                    new Dialog("Play Again ?", new DialogListener() {
                        @Override
                        public void onResponse(DialogResponse response) {
                            if (response == DialogResponse.YES) {
                                startGame(currentLevel);
                            } else {
                                fillTouchWhite();
                                r.setTranslateY(0);
                            }
                        }
                    }, DialogType.QUESTION, DialogOptions.YES_NO, GameController.this.getStage()).show();
                }
            }, DialogType.WARNING, DialogOptions.OK, this.getStage()).show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTouchs();
        this.startGame(currentLevel);
    }

    private void startGame(int level) {
        fillTouchWhite();

        //Level level text
        textLabelChoose.setText("Level " + String.valueOf(level) + " is choosen");

        //Game model init
        levelController = new AlgoHangManController(level);
        wordGuess.setText(levelController.getGuess_word());

        //Reset hangman
        manInit(Color.WHITE, 6);
        manInit(Color.web("bfbfbf"), 1);

        //Stats init
        count.setText("0");
        letterSelectedLabelString.setText(" ");
        helpStringLabel.setText(" ");
        ResultWinLoseLabel.setText(Integer.toString(levelController.getMax_choose()));
    }

    private void manDrawOneByOne(int i) {
        switch (i) {
            case 1:
                gallows_bottom.setStrokeWidth(5);
                gallows_bottom.setStroke(Color.RED);
                break;

            case 2:
                gallows_middle.setStrokeWidth(5);
                gallows_middle.setStroke(Color.RED);
                break;
            case 3:
                gallows_top.setStrokeWidth(5);
                gallows_top.setStroke(Color.RED);
                break;
            case 4:
                gallows_head.setStrokeWidth(3);
                gallows_head.setStroke(Color.RED);
                break;
            case 5:
                legLeft.setStrokeWidth(5);
                legLeft.setStroke(Color.RED);
                break;
            case 6:
                torso.setStrokeWidth(3);
                torso.setStroke(Color.RED);
                break;
            case 7:
                armLeft.setStrokeWidth(5);
                armLeft.setStroke(Color.RED);
                break;
            case 8:
                armRight.setStrokeWidth(5);
                armRight.setStroke(Color.RED);
                break;
            case 9:
                legRight.setStrokeWidth(5);
                legRight.setStroke(Color.RED);
                break;
            case 10:
                head.setFill(Color.RED);
                head.setStroke(Color.ORANGE);//réglage de la couleur de la bordure et de son épaisseur
                head.setStrokeWidth(2);
                break;
        }
    }

    private void manInit(Color a, int b) {
        gallows_bottom.setStrokeWidth(b);
        gallows_bottom.setStroke(a);

        gallows_top.setStrokeWidth(b);
        gallows_top.setStroke(a);

        gallows_middle.setStrokeWidth(b);
        gallows_middle.setStroke(a);

        gallows_head.setStrokeWidth(b);
        gallows_head.setStroke(a);

        head.setFill(Color.WHITE);
        head.setStroke(a);//réglage de la couleur de la bordure et de son épaisseur
        head.setStrokeWidth(2);

        torso.setStrokeWidth(b);
        torso.setStroke(a);

        armLeft.setStrokeWidth(b);
        armLeft.setStroke(a);

        armRight.setStrokeWidth(b);
        armRight.setStroke(a);

        legRight.setStrokeWidth(b);
        legRight.setStroke(a);

        legLeft.setStrokeWidth(b);
        legLeft.setStroke(a);
    }

    private Stage stage;

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public Stage getStage() {
        return this.stage;
    }
}
