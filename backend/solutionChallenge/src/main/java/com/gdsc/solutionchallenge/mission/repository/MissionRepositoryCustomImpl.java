package com.gdsc.solutionchallenge.mission.repository;

import com.gdsc.solutionchallenge.mission.dto.response.MissionListResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MissionRepositoryCustomImpl implements MissionRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<MissionListResponse> findAllWithMissionCompleteResult(Long loginMemberId) {
        List<MissionListResponse> response = em.createQuery(
                        "select new com.gdsc.solutionchallenge.mission.dto.response.MissionListResponse(m.id, m.title, case when mm.missionCompleteStatus is not null then 'true' else 'false' end)" +
                                " from Mission m" +
                                " left join MemberMission mm" +
                                " on m.id = mm.mission.id and mm.member.id = :loginMemberId" +
                                " order by m.id"
                        , MissionListResponse.class)
                .setParameter("loginMemberId", loginMemberId)
                .getResultList();
        return response;
    }
}