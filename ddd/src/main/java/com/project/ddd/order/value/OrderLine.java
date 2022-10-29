package com.project.ddd.order.value;

import com.project.ddd.board.value.BoardId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLine {

    @Embedded
    private BoardId boardId;

    private int quantity;

    private int amount;

    public OrderLine(BoardId boardId, int quantity, int amount) {
        this.boardId = boardId;
        this.quantity = quantity;
        this.amount = amount;
    }


    public static OrderLine of (BoardId boardId, int quantity, int amount){
        return new OrderLine(boardId,quantity,amount);
    }
}

