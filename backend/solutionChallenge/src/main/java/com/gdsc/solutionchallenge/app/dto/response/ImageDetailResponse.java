package com.gdsc.solutionchallenge.app.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageDetailResponse {

    private String name;

    private MultipartFile file;



}
