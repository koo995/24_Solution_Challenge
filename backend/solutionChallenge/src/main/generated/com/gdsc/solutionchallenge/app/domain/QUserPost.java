package com.gdsc.solutionchallenge.app.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserPost is a Querydsl query type for UserPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserPost extends EntityPathBase<UserPost> {

    private static final long serialVersionUID = -33647696L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserPost userPost = new QUserPost("userPost");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QImage image;

    public QUserPost(String variable) {
        this(UserPost.class, forVariable(variable), INITS);
    }

    public QUserPost(Path<? extends UserPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserPost(PathMetadata metadata, PathInits inits) {
        this(UserPost.class, metadata, inits);
    }

    public QUserPost(Class<? extends UserPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.image = inits.isInitialized("image") ? new QImage(forProperty("image"), inits.get("image")) : null;
    }

}

