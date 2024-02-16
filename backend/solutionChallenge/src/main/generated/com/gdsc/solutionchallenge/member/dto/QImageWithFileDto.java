package com.gdsc.solutionchallenge.member.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.gdsc.solutionchallenge.member.dto.QImageWithFileDto is a Querydsl Projection type for ImageWithFileDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QImageWithFileDto extends ConstructorExpression<ImageWithFileDto> {

    private static final long serialVersionUID = -1076729267L;

    public QImageWithFileDto(com.querydsl.core.types.Expression<Long> imageId, com.querydsl.core.types.Expression<String> username, com.querydsl.core.types.Expression<String> fullPath) {
        super(ImageWithFileDto.class, new Class<?>[]{long.class, String.class, String.class}, imageId, username, fullPath);
    }

}

