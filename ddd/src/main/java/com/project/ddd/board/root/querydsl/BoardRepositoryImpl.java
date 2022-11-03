package com.project.ddd.board.root.querydsl;

import com.project.ddd.board.root.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private static final QBoard qBoard = QBoard.board;

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
}
