package com.example.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation = Paths.get("src/main/resources/static/uploads");

    public String storeFile(MultipartFile file) {
        try {
            Files.createDirectories(fileStorageLocation);

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("НЕТУ" + file.getOriginalFilename(), ex);
        }
    }

    public String getFilePath(String fileName) {
        return "/uploads/" + fileName;
    }
}


