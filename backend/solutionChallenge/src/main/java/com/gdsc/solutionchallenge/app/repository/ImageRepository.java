package com.gdsc.solutionchallenge.app.repository;

import com.gdsc.solutionchallenge.app.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {

}
