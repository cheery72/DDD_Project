package com.project.ddd.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ddd.board.root.BoardRepository;
import com.project.ddd.member.application.dto.MemberCreateDto;
import com.project.ddd.member.root.Member;
import com.project.ddd.member.root.MemberRepository;
import com.project.ddd.member.root.MemberService;
import com.project.ddd.order.root.Order;
import com.project.ddd.order.root.OrderRepository;
import com.project.ddd.order.root.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MemberDomainTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    @DisplayName("회원 가입 수행시 쿠폰 제공 비동기 처리 테스트")
    public void joinProvideCoupon() throws InterruptedException {
        MemberCreateDto memberCreateDto = MemberCreateDto.builder()
                .email("user1@naver.com")
                .password("password1")
                .name("김경민")
                .image("이미지1")
                .build();

        Member member = Member.memberCreateBuilder(memberCreateDto);

        when(memberRepository.save(any(Member.class)))
                .thenReturn(member);

        Member newMember = memberService.joinMember(memberCreateDto);

        assertThat(newMember.getCoupons().get(0).getName()).isEqualTo("신규가입 쿠폰");
    }
}


