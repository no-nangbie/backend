package com.nonangbie.board.service;

import com.nonangbie.board.entity.Board;
import com.nonangbie.board.repository.BoardRepository;
import com.nonangbie.boardLike.repository.BoardLikeRepository;
import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository repository;
    private final BoardLikeRepository boardLikeRepository;
    public Board createBoard(Board board, Authentication authentication) {
        //시큐리티 적용 시 내용 추가 예정
        Member member = new Member();
        board.setMember(member);
        return repository.save(board);
    }
    public Board updateBoard(Board board,long boardId,Authentication authentication) {
        //시큐리티 적용 시 내용 추가 예정
        Member member = new Member();
        Board findBoard = repository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        if(!Objects.equals(findBoard.getMember(), member))
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        board.setLikeCount(findBoard.getLikeCount());
        board.setBoardId(findBoard.getBoardId());
        return repository.save(board);
    }

    public void deleteBoard(long boardId,Authentication authentication) {
//        extractMemberFromAuthentication(authentication);
        Board findBoard = findVerifiedBoard(boardId, authentication);
        repository.delete(findBoard);
//        if(!findBoard.getMember().getEmail().equals(authentication.getName())) {
//            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
//        }
//        findBoard.setBoardStatus(Board.BoardStatus.BOARD_DELETED);
//        boardRepository.save(findBoard);
    }
    public Page<Board> findBoardsByType(String type, String sort, int page, int size,Authentication authentication) {
        Sort sortBy;
        Board.MenuCategory menuCategory;
        try {
            menuCategory = Board.MenuCategory.valueOf(type.toUpperCase());
            switch (sort) {
                case "LIKE_ASC":
                    sortBy = Sort.by("likeCount").ascending(); break;
                case "LIKE_DESC":
                    sortBy = Sort.by("likeCount").descending(); break;
                case "CREATED_AT_ASC":
                    sortBy = Sort.by("CREATED_AT").ascending(); break;
                case "CREATED_AT_DESC":
                    sortBy = Sort.by("CREATED_AT").descending(); break;
                default:
                    throw new IllegalArgumentException("Invalid sort type: " + sort);
            }
        } catch (IllegalArgumentException e) {
            throw new BusinessLogicException(ExceptionCode.INVALID_BOARD_FILTER_TYPE);
        }
        Pageable pageable = PageRequest.of(page, size, sortBy);
        return repository.findAllByMenuCategory(menuCategory, pageable);
    }

    public boolean likeBoard(long boardId,Authentication authentication) {
        Board findBoard = repository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        findBoard.setLikeCount(findBoard.getLikeCount() + 1);
        return true;
    }

    @Transactional(readOnly = true)
    public Board findVerifiedBoard(long boardId, Authentication authentication){
        Optional<Board> findBoard =
                repository.findById(boardId);
        Board result = findBoard.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return result;
    }

}
