package com.gdsc.solutionchallenge.member.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 929168232L;

    public static final QMember member = new QMember("member1");

    public final com.gdsc.solutionchallenge.common.domain.QBaseEntity _super = new com.gdsc.solutionchallenge.common.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.gdsc.solutionchallenge.app.domain.Image, com.gdsc.solutionchallenge.app.domain.QImage> image = this.<com.gdsc.solutionchallenge.app.domain.Image, com.gdsc.solutionchallenge.app.domain.QImage>createList("image", com.gdsc.solutionchallenge.app.domain.Image.class, com.gdsc.solutionchallenge.app.domain.QImage.class, PathInits.DIRECT2);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final StringPath uid = createString("uid");

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

