package com.project.ddd.shorts.value;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class ShortsId implements Serializable {

    @Column(name = "shorts_id")
    private String id;

    public ShortsId(String id) {
        this.id = id;
    }

    public static ShortsId of (String id){
        return new ShortsId(id);
    }
}
