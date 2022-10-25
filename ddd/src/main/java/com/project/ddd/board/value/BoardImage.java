package com.project.ddd.board.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardImage {

    private String image;

    public BoardImage(String image) {
        this.image = image;
    }

    public static List<BoardImage> of(List<String> images){
        return images.stream().map(BoardImage::new).collect(Collectors.toList());
    }

    public static List<String> imageBuilder(List<BoardImage> images){
        return images.stream().map(BoardImage::getImage).collect(Collectors.toList());
    }
}
