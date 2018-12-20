package com.step.webServer.service;

import com.step.webServer.exception.DirectoryCreatingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class FileService {
    private final String PICTURE_PATH = System.getProperty("user.home") + File.separator + "webServerPictures";

    @PostConstruct
    private void init() {
        File directory = new File(PICTURE_PATH);
        if(!directory.exists()){
            boolean exists = directory.mkdir();
            if (!exists) {
                throw new DirectoryCreatingException("can't create directory: " + PICTURE_PATH);
            }
        }
    }

    @Async
    public CompletableFuture<String> savePicture(MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("user", "", new File(PICTURE_PATH));
        multipartFile.transferTo(file);
        return CompletableFuture.completedFuture(file.getPath());
    }

}
