package com.gdsc.solutionchallenge.app;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class firebaseAuthTest {

    @Test
    void 테스트1() throws FirebaseAuthException {
        String uid = "E6oH5OgUTgQXejfs7dKx3yMAAcI3";
        UserRecord user = FirebaseAuth.getInstance().getUser(uid);
        long tokensValidAfterTimestamp = user.getTokensValidAfterTimestamp();
        System.out.println("tokensValidAfterTimestamp = " + tokensValidAfterTimestamp);
    }
}
