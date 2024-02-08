package com.gdsc.solutionchallenge.app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserPostRequest {

    private MultipartFile image;

    //todo 사진이 찍힌 날짜, 위도, 경도 추가.


}
