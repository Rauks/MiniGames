/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.gsetupwizard.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Karl
 */
public class ZipVisitor extends SimpleFileVisitor<Path>{
    private final int BUFFER = 2048;
    private final byte data[] = new byte[BUFFER];
    
    private final ZipOutputStream zout;
    private final Path basePath;

    public ZipVisitor(ZipOutputStream zout, Path basePath) {
        this.zout = zout;
        this.basePath = basePath;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String entryName = this.basePath.relativize(file).toString();
        this.zout.putNextEntry(new ZipEntry(entryName));
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file.toFile()), BUFFER)){
            int read = -1;
            while((read = bis.read(this.data, 0, BUFFER)) != -1){
                this.zout.write(this.data, 0, read);
            }
        }
        this.zout.closeEntry();
        return FileVisitResult.CONTINUE;
    }
}
