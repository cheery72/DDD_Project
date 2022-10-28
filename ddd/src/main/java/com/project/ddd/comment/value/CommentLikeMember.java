package com.project.ddd.comment.value;

import com.project.ddd.board.value.BoardLikeMember;
import com.project.ddd.member.value.MemberId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeMember {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "comment_like_member_id"))
    )
    private MemberId memberId;

    public CommentLikeMember(MemberId memberId) {
        this.memberId = memberId;
    }

    public static CommentLikeMember of(MemberId memberId){
        return new CommentLikeMember(memberId);
    }

    public static List<CommentLikeMember> ofList(List<String> likeMembers) {
        List<MemberId> memberIds = likeMembers.stream().map(MemberId::new).collect(Collectors.toList());
        return memberIds.stream().map(CommentLikeMember::new).collect(Collectors.toList());
    }

    public static List<String> likeBuilder(List<CommentLikeMember> likeMembers){
        return Optional.of(likeMembers.stream().map(CommentLikeMember::getMemberId).map(MemberId::getId).collect(Collectors.toList()))
                .orElseGet(Collections::emptyList);
    }
}
