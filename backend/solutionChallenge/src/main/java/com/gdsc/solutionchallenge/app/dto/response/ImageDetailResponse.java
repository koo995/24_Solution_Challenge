package com.gdsc.solutionchallenge.app.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gdsc.solutionchallenge.app.domain.Image;
import com.google.type.LatLng;
import lombok.Getter;

@Getter
public class ImageDetailResponse {

    @JsonProperty("scientific_name")
    private String scientificName;

    @JsonProperty("species_id")
    private Long speciesId;

    private String url;

    @JsonProperty("image_id")
    private Long imageId;

    @JsonProperty("created_at")
    private String createdAt;

    private CustomLatLng location;

    @Getter
    private static class CustomLatLng {
        private double latitude;
        private double longitude;

        public CustomLatLng(LatLng latLng) {
            this.latitude = latLng.getLatitude();
            this.longitude = latLng.getLongitude();
        }

    }

    public ImageDetailResponse(Image image) {
        this.scientificName = image.getSpecies().getScientificName();
        this.speciesId = image.getSpecies().getId();
        this.url = image.getFullPath();
        this.createdAt = image.getCreatedDate();
        this.location = new CustomLatLng(image.getLatLng());
        this.imageId = image.getId();
    }
}