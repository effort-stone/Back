package com.effortstone.backend.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("effort-stone-firebase-admin.json").getInputStream()))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }

    }

    @Bean
    public FirebaseAuth firebaseAuth(FirebaseApp firebaseApp) {
        return FirebaseAuth.getInstance(firebaseApp);      }

}
