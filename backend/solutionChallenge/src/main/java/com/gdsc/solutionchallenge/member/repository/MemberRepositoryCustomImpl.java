package com.gdsc.solutionchallenge.member.repository;

import com.gdsc.solutionchallenge.member.dto.ImageWithFileDto;
import com.gdsc.solutionchallenge.member.dto.ProfileResponseDto;
import com.gdsc.solutionchallenge.member.dto.QImageWithFileDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.gdsc.solutionchallenge.app.domain.QImage.image;
import static com.gdsc.solutionchallenge.member.domain.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    // 혹시 카테고리 분리 할 수 있으니 queryDsl 을 이용하자.
    @Override
    public ProfileResponseDto findByIdWithImage(Long memberId, Pageable pageable) {
        Long total = jpaQueryFactory
                .select(image.count())
                .from(image)
                .where(image.member.id.eq(memberId))
                .fetchOne();

        List<ImageWithFileDto> images = jpaQueryFactory
                .select(new QImageWithFileDto(
                        image.id,
                        member.username,
                        image.fullPath)
                )
                .from(member)
                .join(image).on(image.member.id.eq(member.id))
                .where(member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        if (images.isEmpty()) {
            throw new RuntimeException();
        }
        String username = images.get(0).getUsername();
        PageImpl<ImageWithFileDto> imageWithFileDtos = new PageImpl<>(images, pageable, total);
        return new ProfileResponseDto(username, total, imageWithFileDtos);
        // todo 이렇게 page객체를 다른 dto가 감싸도 되나?
    }
}
