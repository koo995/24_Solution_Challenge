package com.gdsc.solutionchallenge.app.domain;

import jakarta.persistence.*;
import lombok.*;

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

    private String scientificName;

    // todo 한글명, description이 필요

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> image = new ArrayList<>();

    @Builder
    public Species(String scientificName) {
        this.scientificName = scientificName;
    }

    // 연관관계 메서드
    public void addImage(Image image) {
        this.image.add(image);
    }
}
