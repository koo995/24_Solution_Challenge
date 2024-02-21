package com.gdsc.solutionchallenge.mission.controller;

import com.gdsc.solutionchallenge.auth.annotation.Login;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.mission.dto.request.MissionCreateDto;
import com.gdsc.solutionchallenge.mission.dto.response.MissionCreateResponse;
import com.gdsc.solutionchallenge.mission.dto.response.MissionDetail;
import com.gdsc.solutionchallenge.mission.dto.response.MissionListResponse;
import com.gdsc.solutionchallenge.mission.dto.response.MissionResult;
import com.gdsc.solutionchallenge.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/api/v1/mission")
    public MissionCreateResponse create(@RequestBody MissionCreateDto missionCreateDto, @Login Member loginMember) {
        Long missionId = missionService.createMission(missionCreateDto, loginMember);
        return new MissionCreateResponse(missionId);
    }

    @GetMapping("/api/v1/mission")
    public List<MissionListResponse> list(@Login Member loginMember) {
        List<MissionListResponse> response = missionService.getList(loginMember);
        return response;
    }

    @GetMapping("/api/v1/mission/{missionId}")
    public MissionDetail detail(@PathVariable(name = "missionId") Long missionId, @Login Member loginMember) {
        return missionService.detail(missionId, loginMember);
    }

    @PostMapping("/api/v1/mission/{missionId}")
    public MissionResult upload(@PathVariable(name = "missionId") Long missionId,
                                @Login Member loginMember,
                                @RequestParam("file") MultipartFile file) {
        return missionService.imageUpload(missionId, loginMember, file);
    }
}