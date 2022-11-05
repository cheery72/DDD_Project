package com.project.ddd.member.value;

import com.project.ddd.board.value.BoardId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

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

    public static MemberId createMemberId() {
        return new MemberId(UUID.randomUUID().toString().replace("-",""));
    }

    public static MemberId of(String id) {
        return new MemberId(id);
    }
}
