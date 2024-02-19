package com.gdsc.solutionchallenge.app.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "species_id")
    private Long id;

    @Column(name = "scientific_name")
    private String scientificName;

    @Column(name = "korea_name")
    private String koreaName;

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> image = new ArrayList<>();

    @Builder
    public Species(String scientificName, String koreaName) {
        this.scientificName = scientificName;
        this.koreaName = koreaName;
    }

    // 연관관계 메서드
    public void addImage(Image image) {
        this.image.add(image);
    }
}
