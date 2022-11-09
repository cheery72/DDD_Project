package com.project.ddd.member.root;

import com.project.ddd.common.BaseTime;
import com.project.ddd.member.application.dto.MemberCreateDto;
import com.project.ddd.member.value.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private MemberStatus memberStatus;

    @Embedded
    private MemberImage image;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "member_coupon", joinColumns = @JoinColumn(name = "member_id"))
    @OrderColumn(name = "coupon_idx")
    private List<Coupon> coupons = new ArrayList<>();

    public void joinCoupon() {
        this.coupons = List.of(new Coupon("신규가입 쿠폰", 10));
    }

    public static Member memberCreateBuilder(MemberCreateDto memberCreateDto){
        return Member.builder()
                .memberId(MemberId.createMemberId())
                .email(Email.of(memberCreateDto.getEmail()))
                .password(Password.of(memberCreateDto.getPassword()))
                .name(Name.of(memberCreateDto.getName()))
                .memberStatus(MemberStatus.MAINTENANCE)
                .image(MemberImage.of(memberCreateDto.getImage()))
                .build();
    }

    @Builder
    public Member(MemberId memberId, Email email, Password password, Name name, MemberStatus memberStatus, MemberImage image) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.memberStatus = memberStatus;
        this.image = image;
    }
}
