/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.brickbreaker.elements.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.kirauks.minigames.brickbreaker.elements.Bloc;
import net.kirauks.minigames.brickbreaker.elements.Bloc.BlocType;

/**
 *
 * @author Karl
 */
public class LevelDescriptor {
    public enum LevelDatas{
        L1("/net/kirauks/minigames/brickbreaker/res/levels/1.lvl");
        
        private String url;
        private LevelDatas(String url){
            this.url = url;
        }
    }
    
    private ArrayList<ArrayList<Bloc>> datas;
    
    public LevelDescriptor(LevelDatas datas){
        this.datas = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(LevelDescriptor.class.getResourceAsStream(datas.url)))){
            String line;
            while((line = br.readLine()) != null){
                ArrayList<Bloc> lineDatas = new ArrayList<>();
                char[] chars = line.toCharArray();
                for(char c : chars){
                    switch(c){
                        case 'R':
                            lineDatas.add(new Bloc(BlocType.RED));
                            break;
                        case 'G':
                            lineDatas.add(new Bloc(BlocType.GREEN));
                            break;
                        case 'B':
                            lineDatas.add(new Bloc(BlocType.BLUE));
                            break;
                        case 'Y':
                            lineDatas.add(new Bloc(BlocType.YELLOW));
                            break;
                    }
                }
                this.datas.add(lineDatas);
            }
        } catch (IOException ex) {
            Logger.getLogger(LevelDescriptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<ArrayList<Bloc>> getLevelDatas(){
        return this.datas;
    }
}
