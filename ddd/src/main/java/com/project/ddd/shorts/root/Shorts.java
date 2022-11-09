package com.project.ddd.shorts.root;

import com.project.ddd.common.BaseTime;
import com.project.ddd.common.ImageType;
import com.project.ddd.shorts.value.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shorts extends BaseTime {

    @EmbeddedId
    private ShortsId id;

    @Embedded
    private Shortser shortser;

    @Embedded
    private ShortsContent content;

    @Embedded
    private ShortsImage image;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "shorts_tag", joinColumns = @JoinColumn(name = "shorts_id"))
    @OrderColumn(name = "tag_member_idx")
    private List<ShortsTagMember> tags = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @OrderColumn(name = "like_member_idx")
    @CollectionTable(name = "shorts_like_member", joinColumns = @JoinColumn(name = "shorts_id"))
    private List<ShortsLikeMember> likeMembers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "shorts_select_member", joinColumns = @JoinColumn(name = "shorts_id"))
    @OrderColumn(name = "select_member_idx")
    private List<ShortsSelectMember> selectMembers = new ArrayList<>();

    private int likes;

    @Column(name = "image_type")
    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @Builder
    public Shorts(ShortsId id, Shortser shortser, ShortsContent content, List<ShortsTagMember> tags, List<ShortsLikeMember> likeMembers, ShortsImage image, List<ShortsSelectMember> selectMembers, int likes, ImageType imageType) {
        this.id = id;
        this.shortser = shortser;
        this.content = content;
        this.tags = tags;
        this.likeMembers = likeMembers;
        this.image = image;
        this.selectMembers = selectMembers;
        this.likes = likes;
        this.imageType = imageType;
    }
}
