/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.gsetupwizard.tasks;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javafx.concurrent.Task;
import net.kirauks.minigames.gsetupwizard.GuiController;
import net.kirauks.minigames.gsetupwizard.utils.ZipVisitor;

/**
 *
 * @author Karl
 */
public class GsetupTask extends Task<Void> {
    private final File gameFile;
    private final File splashFile;
    private final String gameTitle;
    private final String gameDescription;

    public GsetupTask(String gameTitle, String gameDescription, File gameFile, File splashFile) {
        this.gameFile = gameFile;
        this.splashFile = splashFile;
        this.gameTitle = gameTitle;
        this.gameDescription = gameDescription;
    }
    
    @Override
    protected Void call() throws Exception {
        Path gamePath = Paths.get(this.gameFile.getPath());
        Path basePath = gamePath.getParent();
        Path gameLibs = Paths.get(basePath.toString(), "lib");
        Path gameSplash = Paths.get(this.splashFile.getPath());
        
        String gSetupFileName = gamePath.getFileName().toString();
        Path outGsetup = Paths.get(gSetupFileName.substring(0, gSetupFileName.lastIndexOf(".")).concat(".gsetup"));
        
        try {
            Files.deleteIfExists(outGsetup);
            
            final int BUFFER = 2048;
            byte data[] = new byte[BUFFER];
            try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(outGsetup.toFile()))){
                zout.setMethod(ZipOutputStream.DEFLATED);
                zout.setLevel(Deflater.BEST_COMPRESSION);
                
                //Game .jar
                zout.putNextEntry(new ZipEntry("game.jar"));
                try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(gamePath.toFile()), BUFFER)){
                    int read = -1;
                    while((read = bis.read(data, 0, BUFFER)) != -1){
                        zout.write(data, 0, read);
                    }
                }
                zout.closeEntry();
                
                //Game libs
                if(Files.isDirectory(gameLibs)){
                    Files.walkFileTree(gameLibs, new ZipVisitor(zout, basePath));
                }
                
                //Game splash
                zout.putNextEntry(new ZipEntry("game.splash"));
                try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(gameSplash.toFile()), BUFFER)){
                    int read = -1;
                    while((read = bis.read(data, 0, BUFFER)) != -1){
                        zout.write(data, 0, read);
                    }
                }
                zout.closeEntry();
                
                //Game metadata
                zout.putNextEntry(new ZipEntry("game.metadata"));
                Path tempMeta = Files.createTempFile("gsetup-meta", null);
                try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(tempMeta.toFile()))))){
                    bw.write(this.gameTitle);
                    bw.write(System.lineSeparator());
                    bw.write(this.gameDescription);
                    bw.flush();
                }
                try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(tempMeta.toFile()), BUFFER)){
                    int read = -1;
                    while((read = bis.read(data, 0, BUFFER)) != -1){
                        zout.write(data, 0, read);
                    }
                }
                zout.closeEntry();
            }
        } catch (IOException ex) {
            Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
