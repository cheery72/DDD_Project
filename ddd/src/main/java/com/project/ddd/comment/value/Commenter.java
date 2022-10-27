package com.project.ddd.comment.value;

import com.project.ddd.member.value.MemberId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Commenter implements Serializable {

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "commenter_id"))
    )
    private MemberId memberId;

    @Column(name = "commenter_name")
    private String name;

    @Column(name = "commenter_image")
    private String image;

    public Commenter(MemberId memberId, String name, String image) {
        this.memberId = memberId;
        this.name = name;
        this.image = image;
    }

    public static Commenter of(MemberId memberId, String name, String image){
        return new Commenter(memberId,name,image);
    }
}
