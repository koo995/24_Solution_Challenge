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

    @Override
    public PageImpl<ImageDto> findImageByMemberId(Long memberId, FilterCondition filterCondition, Pageable pageable) {
        Long total = jpaQueryFactory
                .select(image.count())
                .from(image)
                .join(species).on(image.species.id.eq(species.id))
                .where(image.member.id.eq(memberId),
                        StringUtils.hasText(filterCondition.getKingdom()) ? species.kingdom.eq(filterCondition.getKingdom()) : null)
                .fetchOne();

        // todo 조인을 2번 하는데... 여기서 kingdom 값이 없다면 굳이 조인을 또 할 필요가 잇을까?
        // 그리고... 필터링 컨디션이 바뀐다면 이걸 또 반복해야하는데.. 조금 더 빠르게 하기 위해서 캐시도 이용가능하지 않을까?
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
