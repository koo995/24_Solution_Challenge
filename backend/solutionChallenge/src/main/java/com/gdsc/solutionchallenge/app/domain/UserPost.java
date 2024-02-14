package com.gdsc.solutionchallenge.app.domain;

import com.gdsc.solutionchallenge.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
@Entity
public class UserPost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    // 연관관계 메서드
    public void setImage(Image image) {
        this.image = image;
    }
}
