package com.gdsc.solutionchallenge.app.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String image_title;

    private String type;

    @ManyToOne
    @JoinColumn(name = "species_id")
    private Species species;


    // 연관관계 메서드
    @Builder
    public Image(String image_title, String type, Species species) {
        this.image_title = image_title;
        this.type = type;
    }

    public void setSpecies(Species species) {
        this.species = species;
        species.addImage(this);

    }
}
