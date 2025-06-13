package org.taskspfe.pfe.security.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ImageDirectoryInitializer {

    private static final String IMAGE_DIR_PATH = "/app/images"; // change as needed

    @PostConstruct
    public void init() {
        File dir = new File(IMAGE_DIR_PATH);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                System.out.println("Image directory created at: " + dir.getAbsolutePath());
            } else {
                System.err.println("Failed to create image directory at: " + dir.getAbsolutePath());
            }
        } else {
            System.out.println("Image directory already exists: " + dir.getAbsolutePath());
        }
    }
}