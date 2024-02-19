package com.gdsc.solutionchallenge.app.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserImageRequest {

    private MultipartFile file;

    public UserImageRequest(MultipartFile file) {
        this.file = file;
    }
}
