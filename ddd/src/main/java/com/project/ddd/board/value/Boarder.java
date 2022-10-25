package com.project.ddd.board.value;

import com.project.ddd.member.value.MemberId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Boarder {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "boarder_id"))
    )
    private MemberId memberId;

    public Boarder(MemberId memberId) {
        this.memberId = memberId;
    }

    public static Boarder of(MemberId memberId) {
        return new Boarder(memberId);
    }
}
