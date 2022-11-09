package com.project.ddd.shorts.value;

import com.project.ddd.member.value.MemberId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shortser {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "shortser_id"))
    )
    private MemberId memberId;

    public Shortser(MemberId memberId) {
        this.memberId = memberId;
    }

    public static Shortser of(MemberId memberId){
        return new Shortser(memberId);
    }
}
