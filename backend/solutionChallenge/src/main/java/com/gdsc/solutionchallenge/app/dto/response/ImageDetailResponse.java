package com.gdsc.solutionchallenge.app.dto.response;

import com.gdsc.solutionchallenge.app.domain.Image;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImageDetailResponse {

    private String scientificName;

    private String url;

    private LocalDateTime created_at;
    //todo geoJson을 활용한 이미지 좌표 데이터도 전달해야함.


    public ImageDetailResponse(Image image) {
        this.scientificName = image.getSpecies().getScientificName();
        this.url = image.getFullPath();
        this.created_at = image.getCreatedDate();
    }
}