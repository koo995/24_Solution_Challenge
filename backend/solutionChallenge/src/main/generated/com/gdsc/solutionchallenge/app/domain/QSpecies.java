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

    public static final QSpecies species = new QSpecies("species");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Image, QImage> image = this.<Image, QImage>createList("image", Image.class, QImage.class, PathInits.DIRECT2);

    public final StringPath scientificName = createString("scientificName");

    public QSpecies(String variable) {
        super(Species.class, forVariable(variable));
    }

    public QSpecies(Path<? extends Species> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSpecies(PathMetadata metadata) {
        super(Species.class, metadata);
    }

}

