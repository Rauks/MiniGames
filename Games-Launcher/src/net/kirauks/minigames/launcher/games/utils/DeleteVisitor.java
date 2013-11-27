/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.kirauks.minigames.launcher.games.utils;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author Karl
 */
public class DeleteVisitor extends SimpleFileVisitor<Path>{
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        if(attributes.isRegularFile()){
            Files.delete(file);
        }
        return FileVisitResult.CONTINUE;
    }
 
    @Override
    public FileVisitResult postVisitDirectory(Path directory, IOException ioe) throws IOException {
        Files.delete(directory);
        return FileVisitResult.CONTINUE;
    }
}
