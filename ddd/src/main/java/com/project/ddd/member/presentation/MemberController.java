package com.project.ddd.member.presentation;

import com.project.ddd.member.application.dto.MemberCreateDto;
import com.project.ddd.member.root.Member;
import com.project.ddd.member.root.MemberRepository;
import com.project.ddd.member.root.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/create")
    public ResponseEntity<Object> memberJoin(@RequestBody MemberCreateDto memberCreateDto) {
        log.info("member join start -------");

        Member newMember = memberService.joinMember(memberCreateDto);
        memberService.joinProvideCoupon(newMember);
        return ResponseEntity.noContent().build();
    }
}
