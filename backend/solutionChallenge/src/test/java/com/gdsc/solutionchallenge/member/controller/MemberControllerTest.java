package com.gdsc.solutionchallenge.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.solutionchallenge.IntegrationTestSupport;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.gdsc.solutionchallenge.app.domain.Species;
import com.gdsc.solutionchallenge.app.repository.ImageRepository;
import com.gdsc.solutionchallenge.app.repository.SpeciesRepository;
import com.gdsc.solutionchallenge.auth.dto.SignInRequest;
import com.gdsc.solutionchallenge.auth.dto.SignInResponse;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.member.repository.MemberRepository;
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
class MemberControllerTest extends IntegrationTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SpeciesRepository speciesRepository;

    @Autowired
    private ImageRepository imageRepository;

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
        memberRepository.save(Member.builder()
                .uid(uid)
                .email(testEmail)
                .username(username)
                .build());
    }

    @AfterEach
    void delete() throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(uid);
        uid = "";
        idToken = "";
    }

    @DisplayName("사용자의 프로필 정보를 이미지와 함께 가져온다.")
    @Test
    void getProfile() throws Exception {
        // given
        //noinspection OptionalGetWithoutIsPresent
        Member member = memberRepository.findByUid(uid).get();
        ArrayList<Species> speciesList = new ArrayList<>();
        for (int i = 0; i < 20 ; i++ ) {
            Species species = Species.builder()
                    .kingdom("animal")
                    .koreaName("korName" + i)
                    .scientificName("sciName" + i)
                    .build();
            speciesList.add(species);
        }
        ArrayList<Image> imageList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Image image = Image.builder()
                    .fullPath("/*/" + i)
                    .uploadFileName("fileName" + i)
                    .latLng(LatLng.newBuilder()
                            .setLatitude(36.5)
                            .setLongitude(124.5).build())
                    .build();
            image.setSpecies(speciesList.get(i));
            image.setMember(member);
        }
        speciesRepository.saveAll(speciesList);
        imageRepository.saveAll(imageList);


        // when then
        mockMvc.perform(get("/api/v1/auth/profile")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .header("Authorization", "Bearer " + idToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testName"))
                .andExpect(jsonPath("$.totalImage").value("20"))
                .andExpect(jsonPath("$.image.pageable").isNotEmpty())
                .andExpect(jsonPath("$.image.pageable.pageSize").value("10"))
                .andExpect(jsonPath("$.image.pageable.offset").value("0"))
                .andExpect(jsonPath("$.image.content.length()", is(10)));
    }

    @DisplayName("조건이 animal 일 때, 사용자의 프로필 정보를 이미지와 함께 가져온다.")
    @Test
    void getProfileWithAnimal() throws Exception {
        // given
        //noinspection OptionalGetWithoutIsPresent
        Member member = memberRepository.findByUid(uid).get();
        ArrayList<Species> speciesList = new ArrayList<>();
        for (int i = 0; i < 20 ; i++ ) {
            Species species = Species.builder()
                    .kingdom(i % 2 == 0 ? "animal" : "plant")
                    .koreaName("korName" + i)
                    .scientificName("sciName" + i)
                    .build();
            speciesList.add(species);
        }
        ArrayList<Image> imageList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Image image = Image.builder()
                    .fullPath("/*/" + i)
                    .uploadFileName("fileName" + i)
                    .latLng(LatLng.newBuilder()
                            .setLatitude(36.5)
                            .setLongitude(124.5).build())
                    .build();
            image.setSpecies(speciesList.get(i));
            image.setMember(member);
        }
        speciesRepository.saveAll(speciesList);
        imageRepository.saveAll(imageList);


        // when then
        mockMvc.perform(get("/api/v1/auth/profile")
                        .queryParam("page", "0")
                        .queryParam("size", "5")
                        .queryParam("kingdom", "animal")
                        .header("Authorization", "Bearer " + idToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testName"))
                .andExpect(jsonPath("$.totalImage").value("10"))
                .andExpect(jsonPath("$.image.pageable").isNotEmpty())
                .andExpect(jsonPath("$.image.pageable.pageSize").value("5"))
                .andExpect(jsonPath("$.image.pageable.offset").value("0"))
                .andExpect(jsonPath("$.image.totalPages").value("2"))
                .andExpect(jsonPath("$.image.content.length()", is(5)));
    }

    @DisplayName("기록이 없는 사용자의 프로필 정보를 가져온다.")
    @Test
    void getProfileEmpty() throws Exception {
        // when then
        mockMvc.perform(get("/api/v1/auth/profile")
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .header("Authorization", "Bearer " + idToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testName"))
                .andExpect(jsonPath("$.totalImage").value("0"));

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
}