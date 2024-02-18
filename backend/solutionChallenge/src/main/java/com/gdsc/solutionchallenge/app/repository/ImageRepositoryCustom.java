package com.gdsc.solutionchallenge.app.repository;

import com.gdsc.solutionchallenge.member.dto.ImageDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


public interface ImageRepositoryCustom {
     PageImpl<ImageDto> findByMemberId(Long memberId, Pageable pageable);
}
