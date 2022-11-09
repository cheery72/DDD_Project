package com.project.ddd.shorts.value;

import com.project.ddd.member.value.MemberId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortsSelectMember {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "shorts_select_member_id"))
    )
    private MemberId memberId;

    public ShortsSelectMember(MemberId memberId) {
        this.memberId = memberId;
    }

    public static ShortsSelectMember of(MemberId selectMember){
        return new ShortsSelectMember(selectMember);
    }

}
