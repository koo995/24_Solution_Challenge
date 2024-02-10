package com.gdsc.solutionchallenge.app.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserPostRequest {

    private MultipartFile file;

    public UserPostRequest(MultipartFile file) {
        this.file = file;
    }
}
