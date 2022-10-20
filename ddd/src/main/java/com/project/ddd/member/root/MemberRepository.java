package com.project.ddd.member.root;

import com.project.ddd.member.root.Member;
import com.project.ddd.member.value.MemberId;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, MemberId> {
}
