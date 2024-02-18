package com.gdsc.solutionchallenge.app.dto.response;

import com.gdsc.solutionchallenge.app.domain.Image;
import com.google.type.LatLng;
import lombok.Getter;

@Getter
public class ImageDetailResponse {

    private String scientificName;

    private Long speciesId;

    private String url;

    private Long imageId;

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