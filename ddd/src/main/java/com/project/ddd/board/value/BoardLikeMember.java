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

    public static BoardLikeMember of(MemberId memberId){
        return new BoardLikeMember(memberId);
    }

    public static List<BoardLikeMember> ofList(List<String> likeMembers) {
        List<MemberId> memberIds = likeMembers.stream().map(MemberId::new).collect(Collectors.toList());
        return memberIds.stream().map(BoardLikeMember::new).collect(Collectors.toList());
    }

    public static List<String> likeBuilder(List<BoardLikeMember> likeMembers){
        return likeMembers.stream().map(BoardLikeMember::getMemberId).map(MemberId::getId).collect(Collectors.toList());
    }
}
