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

    private String name;

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL) // 종이 사라진다면 그에 해당하는 이미지도 사라져야겠에
    private List<Image> image = new ArrayList<>();

    @Builder
    public Species(String name) {
        this.name = name;
    }

    public void addImage(Image image) {
        this.image.add(image);
    }
}
