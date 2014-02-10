/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lg.hangman;

/**
 *
 * @author Laetitia
 */
public class AlgoHangManController {
    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */

    private int level_default = 1;
    private String word = "";
    private String guess_word = "";
    private String CharsProposed = "";
    private String guess_wordChartoString = "";
    private final int max_choose = 10;

    private int count = 0;
    private Boolean draw = false;
    private Boolean win = false;
    private char guess_word_Char[];
    private char word_Char[];

    private DialogBox d;

    private FileWord fw;
 

    AlgoHangManController(int _Level) {
        level_default = _Level;
        if (level_default == 1) {

            Start(level_default);
            System.out.println(level_default);
        } else if (level_default == 2) {

            Start(level_default);
            System.out.println(level_default);
        } else {

            Start(level_default);
            System.out.println(level_default);
        }
    }

    public final void Start(int level_default) {

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
        this.word = fw.getWord();

        System.out.println("word - chooosen : " + this.word);

        //create guess_word
        for (int i = 0; i < this.word.length(); i++) {
            this.guess_word = this.guess_word + "_";
        }
        System.out.println("word  : " + this.guess_word);
        InitGuess();

    }

    public void InitGuess() {
        if (this.count == this.max_choose) {

            System.out.println("Lose");
        } else {
            //create a char [] of guess_word ----- and word "paulo"
            this.guess_word_Char = this.guess_word.toCharArray();
            this.word_Char = this.word.toCharArray();
            //System.out.println("InitGuess :" + this.guess_word_Char);
            //System.out.println("InitGuess :" + this.word_Char);
        }

    }

    public void Guess(String letter) {
        guess_wordChartoString = new String(this.guess_word_Char);
        String s2 = new String(this.word_Char);
        System.out.println("Guess :s1" + guess_wordChartoString);
        System.out.println("Guess :s2" + s2);


        /*Scanner in = new Scanner(System.in);
         String letter = in.next();
         */
        System.out.println("Guess :word :" + this.word);
        System.out.println("Guess :letter :" + letter);
        System.out.println("Guess :char proposed :" + CharsProposed);
        System.out.println("Guess :coutn :" + this.count);
        System.out.println("Guess :guess_wordChartoString :" + this.guess_wordChartoString);

        if (this.word.contains(letter)) {
            if (this.CharsProposed.contains(letter)) {
                this.count = this.count + 1;
                System.out.println("Wrong ! ");
                this.draw = true;

            } else {
                System.out.println("Rigth   " + this.word.length());
                System.out.println("Rigth   " + this.guess_word_Char.length);

                for (int i = 0; i < this.word.length(); i++) {
                    char aChar = letter.charAt(0);
                    System.out.println("achar " + aChar);
                    char bChar = word_Char[i];
                    System.out.println("bchar i " + bChar);
                    if (aChar == bChar) {
                        guess_word_Char[i] = aChar;
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
            System.out.println("\n\nYou Lost! The word was: " + this.word);
            //d = new DialogBox("Loser ! Play again ?");
            //Lose();
        }
        // Guess();
    }

    public Boolean isWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public String getGuess_wordChartoString() {
        return guess_wordChartoString;
    }

    public void setGuess_wordChartoString(String guess_wordChartoString) {
        this.guess_wordChartoString = guess_wordChartoString;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLevel_default() {
        return level_default;
    }

    public void setLevel_default(int level_default) {
        this.level_default = level_default;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getGuess_word() {
        return guess_word;
    }

    public void setGuess_word(String guess_word) {
        this.guess_word = guess_word;
    }

    public String getCharsProposed() {
        return CharsProposed;
    }

    public void setCharsProposed(String CharsProposed) {
        this.CharsProposed = CharsProposed;
    }

    public Boolean isDraw() {
        return draw;
    }

    public void setDraw(Boolean draw) {
        this.draw = draw;
    }

    public char[] getGuess_word_Char() {
        return guess_word_Char;
    }

    public void setGuess_word_Char(char[] guess_word_Char) {
        this.guess_word_Char = guess_word_Char;
    }

    public char[] getWord_Char() {
        return word_Char;
    }

    public void setWord_Char(char[] word_Char) {
        this.word_Char = word_Char;
    }

    public int getMax_choose() {
        return max_choose;
    }

}
