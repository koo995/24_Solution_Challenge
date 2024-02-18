package com.gdsc.solutionchallenge.member.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.gdsc.solutionchallenge.member.dto.QImageDto is a Querydsl Projection type for ImageDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QImageDto extends ConstructorExpression<ImageDto> {

    private static final long serialVersionUID = -1435947761L;

    public QImageDto(com.querydsl.core.types.Expression<Long> imageId, com.querydsl.core.types.Expression<String> fullPath) {
        super(ImageDto.class, new Class<?>[]{long.class, String.class}, imageId, fullPath);
    }

}

