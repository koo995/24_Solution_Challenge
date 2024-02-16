package com.gdsc.solutionchallenge.mission.controller;

import com.gdsc.solutionchallenge.auth.annotation.Login;
import com.gdsc.solutionchallenge.member.domain.Member;
import com.gdsc.solutionchallenge.mission.dto.MissionRequestDto;
import com.gdsc.solutionchallenge.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MissionController {

    private final MissionService missionService;

    @PostMapping("/mission")
    public void create(@RequestBody MissionRequestDto missionRequestDto, @Login Member loginMember) {
        missionService.createMission(missionRequestDto, loginMember);
    }
}
    // 여기서 어떤 정보를 받을까?
    // 우선 종을 구분해야하니까 종에대한 id을 받아야한다.
    // 그리고 제목, 이미지, 설명,
