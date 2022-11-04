package com.project.ddd.board.root.querydsl;

import com.project.ddd.board.root.Board;
import com.project.ddd.board.root.QBoard;
import com.project.ddd.board.value.*;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;

import javax.persistence.EntityManager;
import java.util.List;

public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private static final QBoard qBoard = QBoard.board;
    private static final QBoardTag qBoardTag = QBoardTag.boardTag;
    private static final QBoardContent qBoardContent = QBoardContent.boardContent;
    private static final QBoardLikeMember qBoardLikeMember = QBoardLikeMember.boardLikeMember;
    private static final QBoardImage qBoardImage = QBoardImage.boardImage;

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Board> findPageAllByBoarder(Pageable pageable, Boarder boarder) {
            return queryFactory
                    .selectFrom(qBoard)
                    .where(qBoard.boarder.eq(boarder))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(qBoard.createDate.desc())
                    .leftJoin(qBoard.tags,qBoardTag)
                    .fetchJoin()
                    .leftJoin(qBoard.likeMembers, qBoardLikeMember)
                    .fetchJoin()
                    .leftJoin(qBoard.images, qBoardImage)
                    .fetchJoin()
                    .fetch();
    }
}
