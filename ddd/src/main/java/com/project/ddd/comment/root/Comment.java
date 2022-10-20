package com.project.ddd.comment.root;


import com.project.ddd.comment.value.*;
import com.project.ddd.common.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTime {

    @EmbeddedId
    private CommentId id;

    @Embedded
    private Commenter commenter;

    @Embedded
    private CommentImage image;

    @Embedded
    private CommentContent content;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "comment_tag", joinColumns = @JoinColumn(name = "comment_id"))
    @OrderColumn(name = "tag_idx")
    private List<CommentTag> tag;

    private int likes;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_status")
    private Status status;

    public Comment(CommentId id, Commenter commenter, CommentImage image, CommentContent content, List<CommentTag> tag, Status status) {
        this.id = id;
        this.commenter = commenter;
        this.image = image;
        this.content = content;
        this.tag = tag;
        this.status = status;
    }
}
