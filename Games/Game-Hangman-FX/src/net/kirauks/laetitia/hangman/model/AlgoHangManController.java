/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.kirauks.laetitia.hangman.model;

/**
 *
 * @author Laetitia
 */
public class AlgoHangManController {
    private int level_default = 1;
    private String guess_word = "";
    private String CharsProposed = "";
    private String guess_wordChartoString = "";
    private final int max_choose = 10;

    private int count = 0;
    private Boolean draw = false;
    private Boolean win = false;
    private char guess_word_Char[];
    private char word_Char[];

    private FileWord fw;

    public AlgoHangManController(int _Level) {
        level_default = _Level;
        if (level_default == 1) {

            start(level_default);
            System.out.println(level_default);
        } else if (level_default == 2) {

            start(level_default);
            System.out.println(level_default);
        } else {

            start(level_default);
            System.out.println(level_default);
        }
    }

    public final void start(int level_default) {

        System.out.println("level choose " + level_default);
        switch (level_default) {
            case 1:
                fw = new FileWord(level_default);
                break;
            case 2:
                fw = new FileWord(level_default);
                break;
            case 3:
                fw = new FileWord(level_default);
                break;
        }
        String word = fw.getWord();

        //create guess_word
        for (int i = 0; i < word.length(); i++) {
            this.guess_word = this.guess_word + "_ ";
        }
        System.out.println("word  : " + this.guess_word);
        initGuess();
    }

    private void initGuess() {
        if (this.count == this.max_choose) {
            System.out.println("Already lose");
        } else {
            //create a char [] of guess_word ----- and word "paulo"
            this.guess_word_Char = this.guess_word.toCharArray();
            this.word_Char = fw.getWord().toCharArray();
            //System.out.println("InitGuess :" + this.guess_word_Char);
            //System.out.println("InitGuess :" + this.word_Char);
        }
    }

    public void Guess(char letter) {
        guess_wordChartoString = new String(this.guess_word_Char);
        String s2 = new String(this.word_Char);
        System.out.println("Guess :s1" + guess_wordChartoString);
        System.out.println("Guess :s2" + s2);

        String word = fw.getWord();

        /*Scanner in = new Scanner(System.in);
         String letter = in.next();
         */
        System.out.println("Guess :word :" + word);
        System.out.println("Guess :letter :" + letter);
        System.out.println("Guess :char proposed :" + CharsProposed);
        System.out.println("Guess :coutn :" + this.count);
        System.out.println("Guess :guess_wordChartoString :" + this.guess_wordChartoString);

        if (word.contains(Character.toString(letter))) {
            if (this.CharsProposed.contains(Character.toString(letter))) {
                this.count = this.count + 1;
                System.out.println("Wrong ! ");
                this.draw = true;

            } else {
                System.out.println("Rigth   " + word.length());
                System.out.println("Rigth   " + this.guess_word_Char.length);

                for (int i = 0; i < word.length(); i++) {
                    char aChar = letter;
                    System.out.println("achar " + aChar);
                    char bChar = word_Char[i];
                    System.out.println("bchar i " + bChar);
                    if (aChar == bChar) {
                        guess_word_Char[2 * i] = aChar;
                    }
                }
                this.draw = false;
            }
        } else {

            this.count = this.count + 1;
            System.out.println("Wrong ! two");
            this.CharsProposed = this.CharsProposed + letter;
            System.out.println("Wrong tow :" + CharsProposed);
            this.draw = true;
        }
        guess_wordChartoString = new String(this.guess_word_Char);

        if (guess_wordChartoString.equals(s2)) {
            System.out.println("WIN");
            this.win = true;
            //d = new DialogBox("Win ! Play again ?");
            //Win();

        }
        if (this.count == this.max_choose) {
            System.out.println("\n\nYou Lost! The word was: " + word);
            //d = new DialogBox("Loser ! Play again ?");
            //Lose();
        }
        // Guess();
    }

    public Boolean isWin() {
        return win;
    }

    public String getGuess_wordChartoString() {
        return guess_wordChartoString;
    }

    public int getCount() {
        return count;
    }

    public int getLevel_default() {
        return level_default;
    }

    public String getWord() {
        return fw.getWord();
    }

    public String getGuess_word() {
        return guess_word;
    }

    public String getCharsProposed() {
        return CharsProposed;
    }

    public Boolean isDraw() {
        return draw;
    }

    public char[] getGuess_word_Char() {
        return guess_word_Char;
    }

    public char[] getWord_Char() {
        return word_Char;
    }

    public int getMax_choose() {
        return max_choose;
    }
}
