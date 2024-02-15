package com.gdsc.solutionchallenge.app.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.type.LatLng;
import lombok.Getter;

@Getter
public class ImageInfoDto {

    @JsonIgnore
    private String scientificName;

    @JsonProperty("image_id")
    private Long imageId;

    @JsonProperty("created_at")
    private String createdAt;

    private CustomLatLng location;

    public ImageInfoDto(String scientificName,Long imageId, String createdAt, LatLng latLng) {
        this.scientificName = scientificName;
        this.imageId = imageId;
        this.createdAt = createdAt;
        this.location = new CustomLatLng(latLng);
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
