package com.gdsc.solutionchallenge.app.repository;

import com.gdsc.solutionchallenge.member.dto.request.FilterCondition;
import com.gdsc.solutionchallenge.member.dto.response.ImageDto;
import com.gdsc.solutionchallenge.member.dto.response.QImageDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.gdsc.solutionchallenge.app.domain.QImage.image;
import static com.gdsc.solutionchallenge.app.domain.QSpecies.species;
import static com.gdsc.solutionchallenge.member.domain.QMember.member;

@RequiredArgsConstructor
public class ImageRepositoryCustomImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 혹시 카테고리 분리 할 수 있으니 queryDsl 을 이용하자.
    @Override
    public PageImpl<ImageDto> findImageByMemberId(Long memberId, FilterCondition filterCondition, Pageable pageable) {
        Long total = jpaQueryFactory
                .select(image.count())
                .from(image)
                .join(species).on(image.species.id.eq(species.id))
                .where(image.member.id.eq(memberId),
                        StringUtils.hasText(filterCondition.getKingdom()) ? species.kingdom.eq(filterCondition.getKingdom()) : null)
                .fetchOne();

        List<ImageDto> images = jpaQueryFactory
                .select(new QImageDto(image.id, image.fullPath))
                .from(member)
                .join(image).on(image.member.id.eq(member.id))
                .join(species).on(image.species.id.eq(species.id))
                .where(member.id.eq(memberId),
                        StringUtils.hasText(filterCondition.getKingdom()) ? species.kingdom.eq(filterCondition.getKingdom()) : null)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        PageImpl<ImageDto> imagePage = new PageImpl<>(images, pageable, total);
        return imagePage;
    }
}
