package com.gdsc.solutionchallenge.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.solutionchallenge.IntegrationTestSupport;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.auth.dto.SignInRequest;
import com.gdsc.solutionchallenge.auth.dto.SignInResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.type.LatLng;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
class SpeciesControllerTest extends IntegrationTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    SpeciesRepository speciesRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static String uid;
    private static String idToken;

    @Value("${firebase.credential.resource-path}")
    private String keyPath;

    private final String firebaseSignInUrl = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";



    @BeforeEach
    void createUserAndSignIn() throws Exception {
        String testEmail = "testUser@example.com";
        String testPassword = "testPassword";
        String username = "testName";
        UserRecord user = createFirebaseUserAccount(testEmail, testPassword, username);
        uid = user.getUid();
        String apiKey = getApiKey();
        SignInResponse signInResponse = signInFirebase(testEmail, testPassword, apiKey);
        idToken = signInResponse.getIdToken();
    }

    private SignInResponse signInFirebase(String testEmail, String testPassword, String apiKey) {
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
        return signInResponse;
    }

    private String getApiKey() throws IOException {
        HashMap map = objectMapper.readValue(new ClassPathResource(keyPath).getInputStream(), HashMap.class);
        String apiKey = (String) map.get("web_api_key");
        return apiKey;
    }

    private static UserRecord createFirebaseUserAccount(String testEmail, String testPassword, String username) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(testEmail)
                .setPassword(testPassword)
                .setDisplayName(username);
        return FirebaseAuth.getInstance().createUser(request);
    }

    @AfterEach
    void delete() throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(uid);
        uid = "";
        idToken = "";
    }

    @DisplayName("특정 종이 나타난 모든 위치를 표현한다.")
    @Test
    void getLocations() throws Exception {
        // given
        ArrayList<Species> speciesList = new ArrayList<>();
        for (int i = 0; i < 10 ; i++ ) {
            Species species = Species.builder()
                    .kingdom("animal")
                    .koreaName("korName" + i)
                    .scientificName("sciName" + i)
                    .build();
            speciesList.add(species);
        }
        Species species = speciesList.get(5);
        ArrayList<Image> imageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Image image = Image.builder()
                    .uploadFileName("fileName" + i)
                    .latLng(LatLng.newBuilder()
                            .setLatitude(36.5)
                            .setLongitude(124.5).build())
                    .build();
            image.setSpecies(species);
        }
        speciesRepository.saveAll(speciesList);
        imageRepository.saveAll(imageList);

        // when
        Long speciesId = species.getId();
        // then
        mockMvc.perform(get("/api/v1/{speciesId}/location", speciesId)
                        .header("Authorization", "Bearer " + idToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scientificName").value("sciName5"))
                .andExpect(jsonPath("$.images.length()", is(10)))
                .andExpect(jsonPath("$.images[0].location.latitude").value("36.5"))
                .andExpect(jsonPath("$.images[0].location.longitude").value("124.5"));

    }

    @DisplayName("이미지가 없는 종을 요청하면 예외가 발생한다.")
    @Test
    void getLocationsException() throws Exception {
        // given
        ArrayList<Species> speciesList = new ArrayList<>();
        for (int i = 0; i < 10 ; i++ ) {
            Species species = Species.builder()
                    .kingdom("animal")
                    .koreaName("korName" + i)
                    .scientificName("sciName" + i)
                    .build();
            speciesList.add(species);
        }
        Species species = speciesList.get(5);
        ArrayList<Image> imageList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Image image = Image.builder()
                    .uploadFileName("fileName" + i)
                    .latLng(LatLng.newBuilder()
                            .setLatitude(36.5)
                            .setLongitude(124.5).build())
                    .build();
            image.setSpecies(species);
        }
        speciesRepository.saveAll(speciesList);
        imageRepository.saveAll(imageList);

        // when
        Long speciesId = species.getId();
        // then
        mockMvc.perform(get("/api/v1/{speciesId}/location", speciesId - 2)
                        .header("Authorization", "Bearer " + idToken)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("찾으시는 종이 없습니다."));
    }

}