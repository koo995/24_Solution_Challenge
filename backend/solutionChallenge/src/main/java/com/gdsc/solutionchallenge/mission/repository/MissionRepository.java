package com.gdsc.solutionchallenge.mission.repository;

import com.gdsc.solutionchallenge.mission.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long>, MissionRepositoryCustom {
}
