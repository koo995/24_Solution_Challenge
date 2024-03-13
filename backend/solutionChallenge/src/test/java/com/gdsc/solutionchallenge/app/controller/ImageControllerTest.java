package com.gdsc.solutionchallenge.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.solutionchallenge.auth.dto.SignInRequest;
import com.gdsc.solutionchallenge.auth.dto.SignInResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileInputStream;
import java.net.URI;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String uid;
    private static String idToken;

    @Value("${firebase.credential.resource-path}")
    private String keyPath;

    private String firebaseSignInUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";

    @BeforeEach
    void createUserAndSignIn() throws Exception {
        // firebase signup
        String testEmail = "testUser@example.com";
        String testPassword = "testPassword";
        String username = "testName";
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(testEmail)
                .setPassword(testPassword)
                .setDisplayName(username);
        UserRecord user = FirebaseAuth.getInstance().createUser(request);
        uid = user.getUid();
        // get firebase project api key
        HashMap map = objectMapper.readValue(new ClassPathResource(keyPath).getInputStream(), HashMap.class);
        String apiKey = (String) map.get("web_api_key");
        // signIn
        RestTemplate restTemplate = new RestTemplate();
        SignInRequest signInRequest = SignInRequest.builder()
                .email(testEmail)
                .password(testPassword)
                .returnSecureToken(true)
                .build();
        URI uri = UriComponentsBuilder
                .fromHttpUrl(firebaseSignInUrl)
                .queryParam("key", apiKey)
                .build().toUri();
        SignInResponse signInResponse = restTemplate.postForObject(uri, signInRequest, SignInResponse.class);
        idToken = signInResponse.getIdToken();
    }

    @AfterEach
    void delete() throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(uid);
        uid = "";
        idToken = "";
    }

    @DisplayName("인증된 사용자만 이미지파일을 입력한다")
    @Test
    void create() throws Exception {
        // given
        String fileName = "lion.jpeg";
        MockMultipartFile file = new MockMultipartFile("file", fileName, "image/jpeg", new FileInputStream("/Users/keonhongkoo/Downloads/"+fileName));

        // when then
        mockMvc.perform(multipart("/api/v1/image")
                        .file(file)
                        .header("Authorization", "Bearer " + idToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scientificName").value("Panthera leo"))
                .andExpect(jsonPath("$.koreaName").value("사자"))
                .andExpect(jsonPath("$.currentScore").value(10));
    }
}