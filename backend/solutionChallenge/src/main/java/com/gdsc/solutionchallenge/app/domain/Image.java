package com.gdsc.solutionchallenge.app.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String uploadFileName;

    private String storeFileName;

    private String type;

    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;


    // 연관관계 메서드
    @Builder
    public Image(String uploadFileName, String storeFileName, String type) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.type = type;
    }

    public void setSpecies(Species species) {
        this.species = species;
        species.addImage(this);

    }
}
