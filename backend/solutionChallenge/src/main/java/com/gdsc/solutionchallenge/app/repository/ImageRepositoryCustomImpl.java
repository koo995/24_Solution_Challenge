package com.gdsc.solutionchallenge.app.repository;

import com.gdsc.solutionchallenge.member.dto.ImageDto;
import com.gdsc.solutionchallenge.member.dto.QImageDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gdsc.solutionchallenge.app.domain.QImage.image;
import static com.gdsc.solutionchallenge.member.domain.QMember.member;

@RequiredArgsConstructor
public class ImageRepositoryCustomImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 혹시 카테고리 분리 할 수 있으니 queryDsl 을 이용하자.
    @Override
    public PageImpl<ImageDto> findByMemberId(Long memberId, Pageable pageable) {
        Long total = jpaQueryFactory
                .select(image.count())
                .from(image)
                .where(image.member.id.eq(memberId))
                .fetchOne();

        List<ImageDto> images = jpaQueryFactory
                .select(new QImageDto(image.id, image.fullPath))
                .from(member)
                .join(image).on(image.member.id.eq(member.id))
                .where(member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        PageImpl<ImageDto> imagePage = new PageImpl<>(images, pageable, total);
        return imagePage;
    }
}
