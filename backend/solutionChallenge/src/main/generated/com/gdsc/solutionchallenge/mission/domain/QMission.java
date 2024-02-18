package com.gdsc.solutionchallenge.mission.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMission is a Querydsl query type for Mission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMission extends EntityPathBase<Mission> {

    private static final long serialVersionUID = 1500162428L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMission mission = new QMission("mission");

    public final com.gdsc.solutionchallenge.common.domain.QBaseEntity _super = new com.gdsc.solutionchallenge.common.domain.QBaseEntity(this);

    //inherited
    public final StringPath createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final ListPath<MemberMission, QMemberMission> memberMission = this.<MemberMission, QMemberMission>createList("memberMission", MemberMission.class, QMemberMission.class, PathInits.DIRECT2);

    public final NumberPath<Integer> participantCount = createNumber("participantCount", Integer.class);

    public final com.gdsc.solutionchallenge.app.domain.QSpecies species;

    public final StringPath title = createString("title");

    public QMission(String variable) {
        this(Mission.class, forVariable(variable), INITS);
    }

    public QMission(Path<? extends Mission> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMission(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMission(PathMetadata metadata, PathInits inits) {
        this(Mission.class, metadata, inits);
    }

    public QMission(Class<? extends Mission> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.species = inits.isInitialized("species") ? new com.gdsc.solutionchallenge.app.domain.QSpecies(forProperty("species")) : null;
    }

}

