package com.gdsc.solutionchallenge.mission.controller;

import com.gdsc.solutionchallenge.auth.annotation.Login;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.mission.dto.request.MissionCreateDto;
import com.gdsc.solutionchallenge.mission.dto.response.MissionListResponse;
import com.gdsc.solutionchallenge.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/mission")
    public Long create(@RequestBody MissionCreateDto missionCreateDto, @Login Member loginMember) {
        return missionService.createMission(missionCreateDto, loginMember);
    }

    @GetMapping("/mission")
    public List<MissionListResponse> list(@Login Member loginMember) {
        List<MissionListResponse> response = missionService.getList(loginMember);
        return response;
    }
}