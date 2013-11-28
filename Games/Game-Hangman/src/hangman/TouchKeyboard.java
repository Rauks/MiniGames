/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Laetitia
 */
public class TouchKeyboard extends Parent {

    private String letter;
    private int posX = 0;
    private int posY = 0;
    private String letterTape=" ";
    Rectangle back_touch;
    private boolean alreadyTouch =false;
    private Text letter_touch;


    public Rectangle getBack_touch() {
        return back_touch;
    }

    public String getLetterTape() {
        return letterTape;
    }
     

    public TouchKeyboard(String _l, int _posX, int _posY, String _letterTape) {
        this.letter = _l;
        this.posX = _posX;
        this.posY = _posY;
        this.letterTape = _letterTape;

        this.back_touch = new Rectangle(30, 30, Color.WHITE);
        this.back_touch.setArcHeight(10);
        this.back_touch.setArcWidth(10);
        this.getChildren().add(back_touch);

        this.letter_touch = new Text(letter);
        this.letter_touch.setFont(new Font(20));
        this.letter_touch.setFill(Color.GREY);
        this.letter_touch.setX(5);
        this.letter_touch.setY(20);

        this.getChildren().add(letter_touch);
        this.setTranslateX(posX);
        this.setTranslateY(posY);

 
    }

    public void pressTouch() {
        back_touch.setFill(Color.DARKGREY);
        this.setTranslateY(posY + 2);
        this.letterTape = letter;
       
        alreadyTouch=true;
    }

    public boolean isAlreadyTouch() {
        return alreadyTouch;
    }

    public void setAlreadyTouch(boolean alreadyTouch) {
        this.alreadyTouch = alreadyTouch;
    }

    public void releaseTouch() {
         
        back_touch.setFill(Color.LIGHTGRAY);
        this.setTranslateY(posY);
    }
    public String getLetter(){
        return this.letter;
    }
    public void setLetter(String _letter){
        this.letter = _letter;
    }

}
