package com.project.ddd.shorts.value;

import com.project.ddd.member.value.MemberId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortsTagMember {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "shorts_tag_member_id"))
    )
    private MemberId memberId;

    public ShortsTagMember(MemberId memberId) {
        this.memberId = memberId;
    }

    public static ShortsTagMember of(MemberId memberId){
        return new ShortsTagMember(memberId);
    }
}
