package com.project.ddd.member.value;

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
public class MemberId implements Serializable {

    @Column(name = "member_id")
    private String id;

    public MemberId(String id) {
        this.id = id;
    }

    public static MemberId of(String id) {
        return new MemberId(id);
    }
}
