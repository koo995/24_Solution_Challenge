package com.gdsc.solutionchallenge.app.repository;

import com.gdsc.solutionchallenge.app.domain.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostRepository extends JpaRepository<UserPost, Long> {
}
