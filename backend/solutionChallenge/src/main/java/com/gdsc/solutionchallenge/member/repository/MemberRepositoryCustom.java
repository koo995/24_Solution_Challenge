package com.gdsc.solutionchallenge.member.repository;

import com.gdsc.solutionchallenge.member.dto.ImageDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public interface MemberRepositoryCustom {
     PageImpl<ImageDto> findByMemberId(Long memberId, Pageable pageable);
}
