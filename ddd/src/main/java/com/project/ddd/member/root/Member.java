package com.project.ddd.member.root;

import com.project.ddd.common.BaseTime;
import com.project.ddd.member.value.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {

    @EmbeddedId
    private MemberId memberId;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Embedded
    private Name name;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_status")
    private MemberStatus isMember;

    @Embedded
    private MemberImage image;

    public Member(MemberId memberId, Email email, Password password, Name name, MemberStatus isMember, MemberImage image) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.isMember = isMember;
        this.image = image;
    }
}
