/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lg.hangman;

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

/**
 *
 * @author Laetitia
 */
public final class FXMLDocumentController implements Initializable {

    private String letterSelected;
    private AlgoHangManController levelController = new AlgoHangManController(1);
    private DialogBox d;
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

    public FXMLDocumentController() {
        this.letterSelected = "";
        fillTouchs();
    }

    public void fillTouchWhite() {
        A.setFill(Color.WHITE);
        Z.setFill(Color.WHITE);
        E.setFill(Color.WHITE);
        R.setFill(Color.WHITE);
        T.setFill(Color.WHITE);
        Y.setFill(Color.WHITE);
        U.setFill(Color.WHITE);
        I.setFill(Color.WHITE);
        O.setFill(Color.WHITE);
        P.setFill(Color.WHITE);
        Q.setFill(Color.WHITE);
        S.setFill(Color.WHITE);
        D.setFill(Color.WHITE);
        F.setFill(Color.WHITE);
        G.setFill(Color.WHITE);
        H.setFill(Color.WHITE);
        J.setFill(Color.WHITE);
        K.setFill(Color.WHITE);
        L.setFill(Color.WHITE);
        M.setFill(Color.WHITE);
        W.setFill(Color.WHITE);
        X.setFill(Color.WHITE);
        C.setFill(Color.WHITE);
        V.setFill(Color.WHITE);
        B.setFill(Color.WHITE);
        N.setFill(Color.WHITE);
    }

    public void fillTouchs() {
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
        System.out.println("You clicked level 1!");
        level2.setDisable(true);
        level3.setDisable(true);
        levelController = new AlgoHangManController(1);
        textLabelChoose.setText("Level 1 is choosen");
        wordGuess.setText(levelController.getGuess_word());
    }

    @FXML
    private void handleButtonActionLevel2(ActionEvent event) {
        System.out.println("You clicked me 2 !");
        textLabelChoose.setText("Level 2 is choosen");
        levelController = new AlgoHangManController(2);
        System.out.println(" Level 2");
        wordGuess.setText(levelController.getGuess_word());
        level1.setDisable(true);
        level3.setDisable(true);
    }

    @FXML
    private void handleButtonActionLevel3(ActionEvent event) {
        System.out.println("You clicked me 3 !");
        System.out.println(" Level 3");
        levelController = new AlgoHangManController(3);
        textLabelChoose.setText("Level 3 is choosen");
        wordGuess.setText(levelController.getGuess_word());
        level2.setDisable(true);
        level1.setDisable(true);
    }

    @FXML
    private void handleButtonActionAbandon(ActionEvent event) {
        System.out.println("You clicked Abandon !");
        d = new DialogBox("Abandon");
        System.out.println(" isAbandon " + d.isAbandonGame());
        if (d.isAbandonGame()) {
            playAgain();
        }
    }

    @FXML
    private void handleButtonActionHelp(ActionEvent event) {
        System.out.println("You clicked Help !");
        helpStringLabel.setText(levelController.getWord());
    }

    @FXML
    private void getBack_touch(MouseEvent event) {
        Rectangle r = (Rectangle) event.getSource();
        System.out.println("getBack : r id :" + r.getId());
        // r.setFill(Color.LIGHTGRAY);
    }

    @FXML
    private void onExited(MouseEvent event) {
        Rectangle r = (Rectangle) event.getSource();
        System.out.println("r id :" + r.getId());
        //   r.setFill(Color.WHITE);
    }

    @FXML
    private void releaseTouch(MouseEvent event) {
        Rectangle r = (Rectangle) event.getSource();
        System.out.println("release id :" + r.getId());
        r.setFill(Color.LIGHTGRAY);
        r.setTranslateY(0);
    }

    @FXML
    private void pressTouch(MouseEvent event) {
        Rectangle r = (Rectangle) event.getSource();
        System.out.println("r id :" + r.getId());
        String myIdLetter = r.getId();
        r.setFill(Color.DARKGREY);
        r.setTranslateY(-3);
        letterSelected = r.getId();
        System.out.println("DrawScene letterSelected :" + letterSelected);
        letterSelectedLabelString.setText(letterSelected);
        System.out.println("DrawScene letterSelected :" + letterSelected);
        levelController.Guess(letterSelected.toLowerCase());

        //updating of word to guess
        wordGuess.setText(levelController.getGuess_wordChartoString());

        //updating to counter
        count.setText(Integer.toString(levelController.getCount()));

        //draw man if wrong letter
        if (levelController.isDraw()) {
            System.out.println("I draw ");
            manDrawOneByOne(levelController.getCount());
        }
        ResultWinLoseLabel.setText("Turns still : " + Integer.toString(levelController.getMax_choose() - levelController.getCount()));
        if (levelController.getCount() == levelController.getMax_choose()) {
            r.setTranslateY(0);

            d = new DialogBox("Loser ! Play again ?");
            if (d.isAbandonGame()) {
                playAgain();
            } else {
                fillTouchWhite();

            }
        }
        if (levelController.isWin()) {
            r.setTranslateY(0);

            d = new DialogBox("Win ! Play again ?");
            if (d.isAbandonGame()) {
                playAgain();
            } else {
                fillTouchWhite();
                r.setTranslateY(0);

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void playAgain() {
        level3.setDisable(false);
        level2.setDisable(false);
        level1.setDisable(false);
        textLabelChoose.setText("Level 1 is choosen by default");

        wordGuess.setText("nothing");
        count.setText("0");
        letterSelectedLabelString.setText(" ");
        helpStringLabel.setText(" ");
        manInit(Color.WHITE, 6);
        manInit(Color.web("000000"), 1);
        /* 
         int i =0;
         System.out.println("size" + touch.size());
         while(i<touch.size()){
         System.out.println("fefrr : " );
         touch.get(i).setFill(Color.WHITE);
         i++;
         }*/
        /*
         for(Rectangle r : touch){
         System.out.println("fefrr : ");
         r.setFill(Color.WHITE);
         }*/
        fillTouchWhite();
        levelController = new AlgoHangManController(1);
        ResultWinLoseLabel.setText("Turns still : " + Integer.toString(levelController.getMax_choose()));
    }

    public void manDrawOneByOne(int i) {
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

    public final void manInit(Color a, int b) {
        gallows_bottom.setStrokeWidth(b);
        gallows_bottom.setStroke(a);

        gallows_top.setStrokeWidth(b);
        gallows_top.setStroke(a);

        gallows_middle.setStrokeWidth(b);
        gallows_middle.setStroke(a);

        gallows_head.setStrokeWidth(b);
        gallows_head.setStroke(a);

        head.setFill(Color.YELLOW);
        head.setStroke(Color.ORANGE);//réglage de la couleur de la bordure et de son épaisseur
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

}
