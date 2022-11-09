package com.project.ddd.shorts.value;


import com.project.ddd.member.value.MemberId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortsLikeMember {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "shorts_like_member_id"))
    )
    private MemberId memberId;

    public ShortsLikeMember(MemberId memberId) {
        this.memberId = memberId;
    }

    public static ShortsLikeMember of(MemberId memberId){
        return new ShortsLikeMember(memberId);
    }
}
