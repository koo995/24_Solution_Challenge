package com.gdsc.solutionchallenge.app.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserPostRequest {

    private MultipartFile image;

    public UserPostRequest(MultipartFile image) {
        this.image = image;
    }
}
