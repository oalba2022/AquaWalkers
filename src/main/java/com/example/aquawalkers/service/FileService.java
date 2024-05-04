package com.example.aquawalkers.service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;



@Service
public class FileService {
    private static final Path FILES_FOLDER = Paths.get(System.getProperty("user.dir"), "files");
    @Value ("src/main/resources/static/files")
    private String uploadDir;

    /*public void saveFile(String folderName, MultipartFile file, String filename) throws IOException {
        Path folder = Paths.get(uploadDir, folderName);
        if(!Files.exists(folder)){
            Files.createDirectories(folder);
        }
        Path newFile = folder.resolve(filename);
        Files.copy(file.getInputStream(), newFile);
    }*/
    public void saveFile(String folderName,  MultipartFile file, String fileName) throws IOException{
        fileName = sanitizeFileName(fileName);
        Path folder = FILES_FOLDER.resolve(folderName);
        Files.createDirectories(folder);
        Path newFile = folder.resolve(fileName);
        file.transferTo(newFile);
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    public void deleteFile(String folderName, String fileName) throws IOException {
        Path folder = Paths.get(uploadDir, folderName);
        Path filePath = folder.resolve(fileName);
        Files.deleteIfExists(filePath);
    }


}
