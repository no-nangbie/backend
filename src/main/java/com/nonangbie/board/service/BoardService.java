package com.nonangbie.board.service;

import com.nonangbie.board.entity.Board;
import com.nonangbie.board.repository.BoardRepository;
import com.nonangbie.boardLike.repository.BoardLikeRepository;
import com.nonangbie.exception.BusinessLogicException;
import com.nonangbie.exception.ExceptionCode;
import com.nonangbie.member.entity.Member;
import com.nonangbie.member.repository.MemberRepository;
import com.nonangbie.utils.ExtractMemberEmail;
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
public class BoardService extends ExtractMemberEmail {
    private final BoardRepository repository;
    private final MemberRepository memberRepository;
    private final BoardLikeRepository boardLikeRepository;
    public Board createBoard(Board board, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        board.setMember(member);
        return repository.save(board);
    }
    public Board updateBoard(Board board,long boardId,Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        Board findBoard = repository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        if(!Objects.equals(findBoard.getMember(), member))
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        board.setMember(member);
        board.setLikeCount(findBoard.getLikeCount());
        board.setBoardId(findBoard.getBoardId());
        return repository.save(board);
    }

    public void deleteBoard(long boardId,Authentication authentication) {
        Board findBoard = findVerifiedBoard(boardId, authentication);
        String email = (String) authentication.getPrincipal();
        if(!findBoard.getMember().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);
        }
        repository.delete(findBoard);
//        findBoard.setBoardStatus(Board.BoardStatus.BOARD_DELETED);
//        boardRepository.save(findBoard);
    }
    public Page<Board> findBoardsByType(String type, String sort, int page, int size,Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
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
                    sortBy = Sort.by("boardId").ascending(); break;
                case "CREATED_AT_DESC":
                    sortBy = Sort.by("boardId").descending(); break;
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
        extractMemberFromAuthentication(authentication, memberRepository);
        Board findBoard = repository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        findBoard.setLikeCount(findBoard.getLikeCount() + 1);
        return true;
    }

    @Transactional(readOnly = true)
    public Board findVerifiedBoard(long boardId, Authentication authentication){
        extractMemberFromAuthentication(authentication, memberRepository);
        Optional<Board> findBoard =
                repository.findById(boardId);
        Board result = findBoard.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return result;
    }

}
