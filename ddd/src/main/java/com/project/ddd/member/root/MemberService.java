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
    public Member joinMember(MemberCreateDto memberCreateDto) {
        Member member = Member.memberCreateBuilder(memberCreateDto);

        return memberRepository.save(member);
    }

    @Transactional
    @Async
    public void joinProvideCoupon(Member member) {
        member.joinCoupon();
        memberRepository.save(member);
    }
}
