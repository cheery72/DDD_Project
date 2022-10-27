package com.project.ddd.comment.value;

import com.project.ddd.board.value.BoardId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class CommentId implements Serializable {

    @Column(name = "comment_id")
    private String id;

    public CommentId(String id) {
        this.id = id;
    }

    public static CommentId createCommentId() {
        return new CommentId(UUID.randomUUID().toString().replace("-",""));
    }

    public static CommentId of(String id){
        return new CommentId(id);
    }
}
