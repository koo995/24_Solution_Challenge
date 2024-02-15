package com.gdsc.solutionchallenge.member.repository;

import com.gdsc.solutionchallenge.member.dto.ProfileResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.awt.print.Pageable;
import java.util.ArrayList;

import static com.gdsc.solutionchallenge.app.domain.QImage.image;
import static com.gdsc.solutionchallenge.member.domain.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    // 혹시 카테고리 분리 할 수 있으니 queryDsl 을 이용하자.
    @Override
    public ProfileResponseDto findByIdWithImage(Long memberId, Pageable pageable) {
        return new ProfileResponseDto("name", 2, new ArrayList<>());
    }
}
