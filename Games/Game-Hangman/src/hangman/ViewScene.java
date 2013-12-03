/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Laetitia
 */
public class ViewScene extends Parent {

    private LevelController levelController = new LevelController(1);

    private TouchKeyboard[] touches;
    String letterSelected;
    private TouchKeyboard t = new TouchKeyboard("A", 20, 20, "A");

    private Label textLabelChoose = new Label(" ");
    private Label letterSelectedLabelString = new Label(" ");
    private Label count = new Label("0");
    private Label wordGuess = new Label(" ");
    private Label ResultWinLoseLabel = new Label("Turns still : " + Integer.toString(levelController.getMax_choose()));
    private Label helpStringLabel = new Label(" ");
        

    private GridPane grid = new GridPane();

    Button level1 = new Button();
    Button level2 = new Button();
    Button level3 = new Button();
    Button abandon = new Button();
    Button help = new Button();
    DialogBox d;

    public ViewScene() {

        //TOP
        Rectangle fond = new Rectangle();
        fond.setWidth(800);
        fond.setHeight(100);
        fond.setFill(Color.BLACK);
        this.getChildren().add(fond);

        textLabelChoose.setTranslateY(100);
        textLabelChoose.setTranslateX(350);
        
       
        help.setText("Help");
        help.setTranslateX(650);
        help.setTranslateY(50);
        
        this.getChildren().add(help);
         help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               helpStringLabel.setText(levelController.getWord());
            }
        });
        
        abandon.setText("Abandon");
        abandon.setTranslateX(550);
        abandon.setTranslateY(50);
        this.getChildren().add(abandon);
        

        abandon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                d = new DialogBox("Abandon");
                System.out.println(" isAbandon " + d.isAbandonGame());
                if (d.isAbandonGame()) {
                    playAgain();
                   
                }
            }
        });

        level1.setText("Level 1");
        level1.setTranslateX(250);
        level1.setTranslateY(50);
        this.getChildren().add(level1);
        level1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //System.out.println(" Level 1");
                level2.setDisable(true);
                level3.setDisable(true);
                levelController = new LevelController(1);
                textLabelChoose.setText("Level 1 is choosen");
                wordGuess.setText(levelController.getGuess_word());

            }
        });

        level2.setText("Level 2");
        level2.setTranslateX(350);
        level2.setTranslateY(50);
        this.getChildren().add(level2);
        level2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                levelController = new LevelController(2);
                System.out.println(" Level 2");
                textLabelChoose.setText("Level 2 is choosen");
                wordGuess.setText(levelController.getGuess_word());
                level1.setDisable(true);
                level3.setDisable(true);
            }
        });

        level3.setText("Level 3");
        level3.setTranslateX(450);
        level3.setTranslateY(50);
        level3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(" Level 3");
                levelController = new LevelController(3);
                textLabelChoose.setText("Level 3 is choosen");
                wordGuess.setText(levelController.getGuess_word());
                level2.setDisable(true);
                level1.setDisable(true);
            }
        });
        this.getChildren().add(level3);
        this.getChildren().add(textLabelChoose);

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle = new Text("Guessing");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label wordGuessLabel = new Label("Word Guess :");
        grid.add(wordGuessLabel, 0, 1);

        grid.add(wordGuess, 1, 1);

        Label countLabel = new Label("Count :");
        grid.add(countLabel, 0, 2);

        grid.add(count, 1, 2);

        Label letterSelectedLabel = new Label("Letters Selected :");
        grid.add(letterSelectedLabel, 0, 3);

        grid.add(letterSelectedLabelString, 1, 3);

        Label helpLabel = new Label("Help : ");
        grid.add(helpLabel,0,4);
        
        grid.add(helpStringLabel, 1, 4);
        
        grid.setTranslateY(150);
        grid.setTranslateX(450);
        this.getChildren().add(grid);

        //LabelChance
        ResultWinLoseLabel.setTranslateX(150);
        ResultWinLoseLabel.setTranslateY(550);
        this.getChildren().add(ResultWinLoseLabel);

        keyBoardShow();

        for (final TouchKeyboard touch : touches) {

            touch.setOnMouseEntered(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent me) {

                    touch.getBack_touch().setFill(Color.LIGHTGRAY);

                }
            });

            touch.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    if (touch.isAlreadyTouch()) {
                        touch.getBack_touch().setFill(Color.LIGHTGRAY);
                    } else {
                        touch.getBack_touch().setFill(Color.WHITE);
                    }

                }
            });
            touch.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    touch.pressTouch();

                    letterSelected = touch.getLetterTape();
                    System.out.println("DrawScene letterSelected :" + letterSelected);
                    letterSelectedLabelString.setText(letterSelected);

                    //letterSelected = letterSelected.toLowerCase();
                    System.out.println("DrawScene letterSelected :" + letterSelected);
                    levelController.Guess(letterSelected.toLowerCase());

                    // System.out.println("count :" + levelController.getCount());
                    
                    //updating of word to guess
                    wordGuess.setText(levelController.getGuess_wordChartoString());
                    
                    //updating to counter
                    count.setText(Integer.toString(levelController.getCount()));
                    
                    //draw man if wrong letter
                    if (levelController.isDraw()) {
                        manDrawOneByOne(levelController.getCount());
                    }
                    ResultWinLoseLabel.setText("Turns still : " + Integer.toString(levelController.getMax_choose() - levelController.getCount()));
                    if(levelController.getCount() == levelController.getMax_choose()){
                         d = new DialogBox("Loser ! Play again ?");
                         if(d.isAbandonGame()){
                             playAgain();
                         }
                    }
                    if(levelController.isWin()){
                         d = new DialogBox("Win ! Play again ?");
                         if(d.isAbandonGame()){
                             playAgain();
                         }
                    }
                }
            });
            touch.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {

                    touch.releaseTouch();
                }
            });
        }

        manInit(Color.web("000000"), 1);

    }

    public final void keyBoardShow() {
        Rectangle keyboard = new Rectangle();
        keyboard.setWidth(355);
        keyboard.setHeight(120);
        keyboard.setArcHeight(30);
        keyboard.setArcWidth(30);
        keyboard.setFill( //on remplie notre rectangle avec un dégradé
                new LinearGradient(0f, 0f, 0f, 1f, true, CycleMethod.NO_CYCLE,
                        new Stop[]{
                            new Stop(0, Color.web("#333333")),
                            new Stop(1, Color.web("#000000"))
                        }
                )
        );
        Reflection r = new Reflection();//on applique un effet de réflection
        r.setFraction(0.25);
        r.setBottomOpacity(0);
        r.setTopOpacity(0.5);
        keyboard.setEffect(r);
        keyboard.setTranslateX(400);
        keyboard.setTranslateY(400);

        touches = new TouchKeyboard[]{
            new TouchKeyboard("A", (int) keyboard.getTranslateX() + 0 + 5, (int) keyboard.getTranslateY() + 5, "a"),
            new TouchKeyboard("Z", (int) keyboard.getTranslateX() + 35 + 5, (int) keyboard.getTranslateY() + 5, "z"),
            new TouchKeyboard("E", (int) keyboard.getTranslateX() + 70 + 5, (int) keyboard.getTranslateY() + 5, "e"),
            new TouchKeyboard("R", (int) keyboard.getTranslateX() + 105 + 5, (int) keyboard.getTranslateY() + 5, "r"),
            new TouchKeyboard("T", (int) keyboard.getTranslateX() + 140 + 5, (int) keyboard.getTranslateY() + 5, "t"),
            new TouchKeyboard("Y", (int) keyboard.getTranslateX() + 175 + 5, (int) keyboard.getTranslateY() + 5, "y"),
            new TouchKeyboard("U", (int) keyboard.getTranslateX() + 210 + 5, (int) keyboard.getTranslateY() + 5, "u"),
            new TouchKeyboard("I", (int) keyboard.getTranslateX() + 245 + 5, (int) keyboard.getTranslateY() + 5, "i"),
            new TouchKeyboard("O", (int) keyboard.getTranslateX() + 280 + 5, (int) keyboard.getTranslateY() + 5, "o"),
            new TouchKeyboard("P", (int) keyboard.getTranslateX() + 316 + 5, (int) keyboard.getTranslateY() + 5, "p"),
            new TouchKeyboard("Q", (int) keyboard.getTranslateX() + 0 + 5, (int) keyboard.getTranslateY() + 40, "q"),
            new TouchKeyboard("S", (int) keyboard.getTranslateX() + 35 + 5, (int) keyboard.getTranslateY() + 40, "s"),
            new TouchKeyboard("D", (int) keyboard.getTranslateX() + 70 + 5, (int) keyboard.getTranslateY() + 40, "d"),
            new TouchKeyboard("F", (int) keyboard.getTranslateX() + 105 + 5, (int) keyboard.getTranslateY() + 40, "f"),
            new TouchKeyboard("G", (int) keyboard.getTranslateX() + 140 + 5, (int) keyboard.getTranslateY() + 40, "g"),
            new TouchKeyboard("H", (int) keyboard.getTranslateX() + 175 + 5, (int) keyboard.getTranslateY() + 40, "h"),
            new TouchKeyboard("J", (int) keyboard.getTranslateX() + 210 + 5, (int) keyboard.getTranslateY() + 40, "j"),
            new TouchKeyboard("K", (int) keyboard.getTranslateX() + 245 + 5, (int) keyboard.getTranslateY() + 40, "k"),
            new TouchKeyboard("L", (int) keyboard.getTranslateX() + 280 + 5, (int) keyboard.getTranslateY() + 40, "l"),
            new TouchKeyboard("M", (int) keyboard.getTranslateX() + 316 + 5, (int) keyboard.getTranslateY() + 40, "m"),
            new TouchKeyboard("W", (int) keyboard.getTranslateX() + 0 + 5, (int) keyboard.getTranslateY() + 80 - 5,"w"),
            new TouchKeyboard("X", (int) keyboard.getTranslateX() + 35 + 5, (int) keyboard.getTranslateY() + 80 - 5, "x"),
            new TouchKeyboard("C", (int) keyboard.getTranslateX() + 70 + 5, (int) keyboard.getTranslateY() + 80 - 5, "c"),
            new TouchKeyboard("V", (int) keyboard.getTranslateX() + 105 + 5, (int) keyboard.getTranslateY() + 80 - 5, "v"),
            new TouchKeyboard("B", (int) keyboard.getTranslateX() + 140 + 5, (int) keyboard.getTranslateY() + 80 - 5, "b"),
            new TouchKeyboard("N", (int) keyboard.getTranslateX() + 175 + 5, (int) keyboard.getTranslateY() + 80 - 5, "n"),
            new TouchKeyboard("-", (int) keyboard.getTranslateX() + 210 + 5, (int) keyboard.getTranslateY() + 80 - 5, "-"),};

        this.getChildren().add(keyboard);
        this.getChildren().addAll(Arrays.asList(touches));

    }

    public final void manInit(Color a, int b) {
        Line gallows_bottum = new Line(100, 500, 200, 500);
        gallows_bottum.setStrokeWidth(b);
        gallows_bottum.setStroke(a);
        this.getChildren().add(gallows_bottum);

        Line gallows_top = new Line(150, 250, 250, 250);
        gallows_top.setStrokeWidth(b);
        gallows_top.setStroke(a);
        this.getChildren().add(gallows_top);

        Line gallows_center = new Line(150, 500, 150, 250);
        gallows_center.setStrokeWidth(b);
        gallows_center.setStroke(a);
        this.getChildren().add(gallows_center);

        Line gallows_head = new Line(250, 250, 250, 280);
        gallows_head.setStrokeWidth(b);
        gallows_head.setStroke(a);
        this.getChildren().add(gallows_head);

        Circle head = new Circle();
        head.setCenterX(250);//réglage de la position, de la taille et de la couleur du cercle
        head.setCenterY(290);
        head.setRadius(10);
        head.setFill(Color.YELLOW);
        head.setStroke(Color.ORANGE);//réglage de la couleur de la bordure et de son épaisseur
        head.setStrokeWidth(2);
        this.getChildren().add(head);

        Line torso = new Line(250, 300, 250, 350);
        torso.setStrokeWidth(b);
        torso.setStroke(a);
        this.getChildren().add(torso);

        Line armLeft = new Line(250, 320, 220, 290);
        armLeft.setStrokeWidth(b);
        armLeft.setStroke(a);
        this.getChildren().add(armLeft);

        Line armRight = new Line(250, 320, 280, 290);
        armRight.setStrokeWidth(b);
        armRight.setStroke(a);
        this.getChildren().add(armRight);

        Line legRight = new Line(250, 350, 280, 380);
        legRight.setStrokeWidth(b);
        legRight.setStroke(a);
        this.getChildren().add(legRight);

        Line legLeft = new Line(250, 350, 220, 380);
        legLeft.setStrokeWidth(b);
        legLeft.setStroke(a);
        this.getChildren().add(legLeft);
    }

    public void manDrawOneByOne(int i) {
        switch (i) {

            case 1:
                Line gallows_bottum = new Line(100, 500, 200, 500);
                gallows_bottum.setStrokeWidth(5);
                gallows_bottum.setStroke(Color.RED);
                this.getChildren().add(gallows_bottum);

                break;

            case 2:
                Line gallows_center = new Line(150, 500, 150, 250);
                gallows_center.setStrokeWidth(5);
                gallows_center.setStroke(Color.RED);
                this.getChildren().add(gallows_center);

                break;
            case 3:
                Line gallows_top = new Line(150, 250, 250, 250);
                gallows_top.setStrokeWidth(5);
                gallows_top.setStroke(Color.RED);
                this.getChildren().add(gallows_top);

                break;
            case 4:
                Line gallows_head = new Line(250, 250, 250, 280);
                gallows_head.setStrokeWidth(3);
                gallows_head.setStroke(Color.RED);
                this.getChildren().add(gallows_head);

                break;
            case 5:
                Line legLeft = new Line(250, 350, 220, 380);
                legLeft.setStrokeWidth(5);
                legLeft.setStroke(Color.RED);
                this.getChildren().add(legLeft);
                break;

            case 6:
                Line torso = new Line(250, 300, 250, 350);
                torso.setStrokeWidth(3);
                torso.setStroke(Color.RED);
                this.getChildren().add(torso);

                break;
            case 7:
                Line armLeft = new Line(250, 320, 220, 290);
                armLeft.setStrokeWidth(5);
                armLeft.setStroke(Color.RED);
                this.getChildren().add(armLeft);
                break;
            case 8:
                Line armRight = new Line(250, 320, 280, 290);
                armRight.setStrokeWidth(5);
                armRight.setStroke(Color.RED);
                this.getChildren().add(armRight);
                break;
            case 9:
                Line legRight = new Line(250, 350, 280, 380);
                legRight.setStrokeWidth(5);
                legRight.setStroke(Color.RED);
                this.getChildren().add(legRight);
                break;

            case 10:
                Circle head = new Circle();
                head.setCenterX(250);//réglage de la position, de la taille et de la couleur du cercle
                head.setCenterY(290);
                head.setRadius(10);
                head.setFill(Color.RED);
                head.setStroke(Color.ORANGE);//réglage de la couleur de la bordure et de son épaisseur
                head.setStrokeWidth(2);
                this.getChildren().add(head);

                break;
        }
    }

    public void playAgain(){
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
                    for (final TouchKeyboard touch : touches) {
                        touch.getBack_touch().setFill(Color.WHITE);
                         touch.setAlreadyTouch(false);
                    }
                    levelController = new LevelController(1);

                    ResultWinLoseLabel.setText("Turns still : " + Integer.toString(levelController.getMax_choose()));}
}
