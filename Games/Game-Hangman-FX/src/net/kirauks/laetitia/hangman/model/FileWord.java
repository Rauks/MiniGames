/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.kirauks.laetitia.hangman.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Laetitia
 */
public final class FileWord {
    private static final String FILE_WORDS_1_2_3 = "res/Word_1_2_3.txt";
    private static final String FILE_WORDS_4_5_6 = "res/Word_4_5_6.txt";
    private static final String FILE_WORDS_7 = "res/Word_7.txt";

    private String word;
    
    public FileWord(int level) {
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(FileWord.class.getResourceAsStream(this.whatMyFile(level))));){
            List<String> words = new ArrayList<>();
            Scanner scanner = new Scanner(bf);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                words.add(line);
            }
            
            Collections.shuffle(words);
            if(!words.isEmpty()){
                this.word = words.get(0);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(FileWord.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String whatMyFile(int a) {
        if (a == 1) {
            return FILE_WORDS_4_5_6;
        } else if (a == 2) {
            return FILE_WORDS_7;
        } else if (a == 3) {
            return FILE_WORDS_1_2_3;
        } else {
            return FILE_WORDS_1_2_3;
        }
    }

    public String getWord() {
        return word;
    }
    
    
/*
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
*/
}
