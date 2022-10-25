package com.project.ddd.board.value;

import com.project.ddd.member.value.MemberId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLikeMember {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "board_like_member_id"))
    )
    private MemberId memberId;

    public BoardLikeMember(MemberId memberId) {
        this.memberId = memberId;
    }

    public static List<BoardLikeMember> of(List<String> likeMembers) {
        List<MemberId> memberIds = likeMembers.stream().map(MemberId::new).collect(Collectors.toList());
        return memberIds.stream().map(BoardLikeMember::new).collect(Collectors.toList());
    }
}
