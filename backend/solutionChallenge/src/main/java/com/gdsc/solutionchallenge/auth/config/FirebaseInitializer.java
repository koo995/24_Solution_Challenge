package com.gdsc.solutionchallenge.auth.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Component
public class FirebaseInitializer {

    @Value("${firebase.credential.resource-path}")
    private String keyPath;

    @PostConstruct
    public void firebaseApp() throws IOException {
        Resource resource = new ClassPathResource(keyPath);
        log.info("Initializing Firebase.");
        FileInputStream serviceAccount =
                new FileInputStream(resource.getFile());
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        log.info("FirebaseApp initialized" + app.getName());
    }
}
