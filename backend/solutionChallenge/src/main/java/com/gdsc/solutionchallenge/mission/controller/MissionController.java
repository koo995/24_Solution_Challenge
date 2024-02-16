package com.gdsc.solutionchallenge.mission.controller;

import com.gdsc.solutionchallenge.auth.annotation.Login;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.mission.dto.request.MissionCreateDto;
import com.gdsc.solutionchallenge.mission.dto.response.MissionDetail;
import com.gdsc.solutionchallenge.mission.dto.response.MissionListResponse;
import com.gdsc.solutionchallenge.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/api/v1/mission")
    public Long create(@RequestBody MissionCreateDto missionCreateDto, @Login Member loginMember) {
        return missionService.createMission(missionCreateDto, loginMember);
    }

    @GetMapping("/api/v1/mission")
    public List<MissionListResponse> list(@Login Member loginMember) {
        List<MissionListResponse> response = missionService.getList(loginMember);
        return response;
    }

    @GetMapping("/api/v1/mission/{missionId}")
    public MissionDetail detail(@PathVariable(name = "missionId") Long missionId) {
        return missionService.detail(missionId);
    }

    @PostMapping("/api/v1/mission/{missionId}")
    public Long upload(@PathVariable(name = "missionId") Long missionId,
                       @Login Member loginMember,
                       @RequestParam("file") MultipartFile file) {
        Long imageId = missionService.imageUpload(missionId, loginMember, file);
        return imageId;
    }
}