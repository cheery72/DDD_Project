package com.project.ddd.member.root;

import com.project.ddd.member.root.Member;
import com.project.ddd.member.value.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends JpaRepository<Member, MemberId> {
}
