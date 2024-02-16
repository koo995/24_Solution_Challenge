package com.gdsc.solutionchallenge.app.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSpecies is a Querydsl query type for Species
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSpecies extends EntityPathBase<Species> {

    private static final long serialVersionUID = -476930057L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSpecies species = new QSpecies("species");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Image, QImage> image = this.<Image, QImage>createList("image", Image.class, QImage.class, PathInits.DIRECT2);

    public final com.gdsc.solutionchallenge.mission.domain.QMission mission;

    public final StringPath scientificName = createString("scientificName");

    public QSpecies(String variable) {
        this(Species.class, forVariable(variable), INITS);
    }

    public QSpecies(Path<? extends Species> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSpecies(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSpecies(PathMetadata metadata, PathInits inits) {
        this(Species.class, metadata, inits);
    }

    public QSpecies(Class<? extends Species> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mission = inits.isInitialized("mission") ? new com.gdsc.solutionchallenge.mission.domain.QMission(forProperty("mission"), inits.get("mission")) : null;
    }

}

