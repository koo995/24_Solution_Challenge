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
import java.io.InputStream;

@Slf4j
@Component
public class FirebaseInitializer {

    @Value("${firebase.credential.resource-path}")
    private String keyPath;

    @PostConstruct
    public void firebaseApp() throws IOException {
        ClassPathResource resource = new ClassPathResource(keyPath);
        log.info("Initializing Firebase.");
        InputStream inputStream = resource.getInputStream();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build();

        if (FirebaseApp.getApps().size() == 0) {
            FirebaseApp.initializeApp(options);
        } else {
            FirebaseApp.getApps();
        }
    }
}
