package pl.sidor.fileUpload.domain.model.base;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@MappedSuperclass
public abstract class BaseEntity<PK> implements Serializable {

    private static final long serialVersionUID = 6550892975762323491L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    PK id;
}
