/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lg.hangman;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Laetitia
 */
public final class FileWord {

    private final Random rand = new Random(System.currentTimeMillis());
    private String word = "";
    private int int_random = 0;

    private final String nameFileWord_1_2_3 = "/JavaFXApplication5/src/ressources/lg/hangman/Word_1_2_3.txt";
    private final String nameFileWord_4_5_6 = "/ressources/lg/hangman/Word_4_5_6.txt";

    private final String nameFileWord_7 = "/JavaFXApplication5/src/ressources/lg/hangman/Word_7.txt";


    public FileWord(int level) {

        System.out.println("FileWord ");
        Scanner scanner = null;
        int countLine;
      
        try {
           // URL res = nnullew URL();
           // URL res = this.getClass().getClassLoader().getResource(WhatIsmyFile(level));
         //   System.out.println("File path : res" +res.getPath());
          //              System.out.println("File path : res" +res.getFile());

           // System.out.print(res.toString());
           //  File f1  = new File( "ressources/lg/hangman/Word_4_5_6.txt");
            
           // FileReader f = new FileReader(f1);
          //  InputStream configStream = this.getClass().getResourceAsStream("Word_4_5_6.txt");
         //  C:\Users\Laetitia\Documents\GitHub\MiniGames\Games\JavaFXApplication5\src\ressources\lg\hangman\Word_1_2_3.txt
//BufferedReader configReader = new BufferedReader(new InputStreamReader(configStream, "UTF-8"));
            BufferedReader bf = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("Word_1_2_3.txt")));
            scanner = new Scanner(bf);
            int maxLine = count(WhatIsmyFile(level));
          
            System.out.println("maxLine" + maxLine);
            this.int_random = this.rand.nextInt(maxLine);
            
            System.out.println("int_random" + this.int_random);
            countLine = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //System.out.println("line " + line);
                if (countLine == this.int_random) {
                    this.word = line;
                }
                countLine++;
            }
        } catch (IOException ex) {
            Logger.getLogger(FileWord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SplitDicoFile(String nameFile) {
        File file1, file2, file3 = null;
        try {
            FileReader f = new FileReader(new File(nameFile));
            BufferedReader bf = new BufferedReader(f);
            Scanner scanner = new Scanner(bf);

            file1 = new File("C:\\Users\\Laetitia\\Documents\\Telechargement_Firefox\\Word_1_2_3.txt");
            file2 = new File("C:\\Users\\Laetitia\\Documents\\Telechargement_Firefox\\Word_5_6.txt");
            file3 = new File("C:\\Users\\Laetitia\\Documents\\Telechargement_Firefox\\Word_7.txt");

            if (file1.createNewFile()) {
                System.out.println("File is created!");
                FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
                BufferedWriter bw1 = new BufferedWriter(fw1);
                FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
                BufferedWriter bw2 = new BufferedWriter(fw2);
                FileWriter fw3 = new FileWriter(file3.getAbsoluteFile());
                BufferedWriter bw3 = new BufferedWriter(fw3);
                int countLine = 0;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (line.length() < 4) {
                        bw1.write(line);
                        bw1.newLine();
                    }
                    if (line.length() < 7 && line.length() > 3) {
                        bw2.write(line);
                        bw2.newLine();
                    }
                    if (line.length() > 6 && line.length() < 8) {
                        bw3.write(line);
                        bw3.newLine();
                    }

                    countLine++;
                }
                bw1.close();
                bw2.close();
                bw3.close();
            } else {
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int count(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }

    public String WhatIsmyFile(int a) {
        if (a == 1) {
            return this.nameFileWord_4_5_6;
        } else if (a == 2) {
            return this.nameFileWord_7;
        } else if (a == 3) {
            return this.nameFileWord_1_2_3;
        } else {
            return this.nameFileWord_1_2_3;
        }
    }

    public int randInt() {
        return int_random;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
