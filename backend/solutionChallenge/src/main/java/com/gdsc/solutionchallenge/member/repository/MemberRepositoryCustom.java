package com.gdsc.solutionchallenge.member.repository;

import com.gdsc.solutionchallenge.member.dto.ProfileResponseDto;
import org.springframework.data.domain.Pageable;


public interface MemberRepositoryCustom {
    ProfileResponseDto findByIdWithImage(Long memberId, Pageable pageable);
}
