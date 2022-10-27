package com.project.ddd.board.value;

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
public class BoardId implements Serializable {

    @Column(name = "board_id")
    private String id;

    public BoardId(String id) {
        this.id = id;
    }

    public static BoardId createBoardId() {
        return new BoardId(UUID.randomUUID().toString().replace("-",""));
    }

    public static BoardId of(String id){
        return new BoardId(id);
    }
}
