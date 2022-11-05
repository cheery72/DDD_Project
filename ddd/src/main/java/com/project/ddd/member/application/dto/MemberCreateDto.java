package com.project.ddd.member.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MemberCreateDto {

    private String email;

    private String password;

    private String name;

    private String image;

}
