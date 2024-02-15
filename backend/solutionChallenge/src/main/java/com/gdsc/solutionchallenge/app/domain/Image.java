package com.gdsc.solutionchallenge.app.domain;

import com.gdsc.solutionchallenge.app.domain.converter.LatLngConverter;
import com.gdsc.solutionchallenge.common.domain.BaseEntity;
import com.google.type.LatLng;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String uploadFileName;

    private String fullPath;

    private String type;

    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;

    @Convert(converter = LatLngConverter.class)
    private LatLng latLng;


    @Builder
    public Image(String uploadFileName, String fullPath, String type, LatLng latLng) {
        this.uploadFileName = uploadFileName;
        this.fullPath = fullPath;
        this.type = type;
        this.latLng = latLng;
    }

    // 연관관계 메서드
    public void setSpecies(Species species) {
        this.species = species;
        species.addImage(this);
    }
}
