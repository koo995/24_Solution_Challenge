package com.gdsc.solutionchallenge.mission.repository;

import com.gdsc.solutionchallenge.mission.dto.response.MissionListResponse;

import java.util.List;

public interface MissionRepositoryCustom {

    List<MissionListResponse> findAllWithMissionCompleteResult(Long id);
}
