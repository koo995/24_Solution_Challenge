package com.gdsc.solutionchallenge.app.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.type.LatLng;
import lombok.Getter;

@Getter
public class SpeciesImageQuery {

    @JsonIgnore
    private String scientificName;

    @JsonIgnore
    private String koreaName;

    private Long imageId;

    private String createdAt;

    private CustomLatLng location;

    public SpeciesImageQuery(String scientificName, String koreaName, Long imageId, String createdAt, LatLng latLng) {
        this.scientificName = scientificName;
        this.koreaName = koreaName;
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
