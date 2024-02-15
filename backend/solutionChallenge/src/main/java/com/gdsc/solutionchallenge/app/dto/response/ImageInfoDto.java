package com.gdsc.solutionchallenge.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.google.type.LatLng;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageInfoDto {

    @JsonProperty("image_id")
    private Long imageId;

    @JsonProperty("created_at")
    private String createdAt;

    private CustomLatLng location;

    @Builder
    public ImageInfoDto(Image image) {
        this.imageId = image.getId();
        this.createdAt = image.getCreatedDate();
        this.location = new CustomLatLng(image.getLatLng());
    }

    @Getter
    private static class CustomLatLng {
        private double latitude;
        private double longitude;

        public CustomLatLng(LatLng latLng) {
            this.latitude = latLng.getLatitude();
            this.longitude = latLng.getLongitude();
        }

    }



}
