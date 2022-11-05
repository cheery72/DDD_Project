package com.project.ddd.member.root;

import com.project.ddd.member.application.dto.MemberCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member joinMember(MemberCreateDto memberCreateDto) throws InterruptedException {
        Member member = Member.memberCreateBuilder(memberCreateDto);
        Member newMember = memberRepository.save(member);
        joinProvideCoupon(newMember);

        return newMember;
    }

    @Async("couponAsync")
    public void joinProvideCoupon(Member member) throws InterruptedException {
        Thread.sleep(3000);
        member.joinCoupon();
    }
}
