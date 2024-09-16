package com.nonangbie.board.service;

import com.nonangbie.S3.service.S3Uploader;
import com.nonangbie.board.entity.Board;
import com.nonangbie.board.repository.BoardRepository;
import com.nonangbie.boardLike.entity.BoardLike;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService extends ExtractMemberEmail {
    private final BoardRepository repository;
    private final MemberRepository memberRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final S3Uploader s3Uploader;



    public void createMenu(MultipartFile image) throws IOException {
        if(image.isEmpty()) {
            throw new IllegalArgumentException("이미지가 없습니다.");
        }
        String storedFileName = s3Uploader.upload(image,"images");
    }


    public Board createBoard(Board board,MultipartFile multipartFile, Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        try{
            String imageUrl = s3Uploader.upload(multipartFile,"boards");
            board.setImageUrl(imageUrl);
            board.setMember(member);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return repository.save(board);
    }
    public Board updateBoard(Board board,MultipartFile multipartFile,
                             long boardId,Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        Board findBoard = repository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        if(!Objects.equals(findBoard.getMember(), member))
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        if(multipartFile == null || multipartFile.isEmpty()) {
            board.setImageUrl(findBoard.getImageUrl());
        }else{
            try{
                String imageUrl = s3Uploader.updateFile(multipartFile,"boards", findBoard.getImageUrl());
                board.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        board.setMember(member);
        board.setLikeCount(findBoard.getLikeCount());
        board.setBoardId(findBoard.getBoardId());
        return repository.save(board);
    }

    public void deleteBoard(long boardId,Authentication authentication) {
        Board findBoard = findVerifiedBoard(boardId, authentication);
        String email = (String) authentication.getPrincipal();
        if(!findBoard.getMember().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
        repository.delete(findBoard);
//        findBoard.setBoardStatus(Board.BoardStatus.BOARD_DELETED);
//        boardRepository.save(findBoard);
    }
    public Page<Board> findBoardsByType(String type, String sort, int page, int size,Authentication authentication) {
        extractMemberFromAuthentication(authentication, memberRepository);
        Sort sortBy;
        Board.MenuCategory menuCategory;
        Pageable pageable;
        switch (sort) {
            case "LIKE_LIST":
                pageable = PageRequest.of(page, size); break;
            case "LIKE_ASC":
                sortBy = Sort.by("likeCount").ascending();
                pageable = PageRequest.of(page, size, sortBy); break;
            case "LIKE_DESC":
                sortBy = Sort.by("likeCount").descending();
                pageable = PageRequest.of(page, size, sortBy); break;
            case "CREATED_AT_ASC":
                sortBy = Sort.by("boardId").ascending();
                pageable = PageRequest.of(page, size, sortBy); break;
            case "CREATED_AT_DESC":
                sortBy = Sort.by("boardId").descending();
                pageable = PageRequest.of(page, size, sortBy); break;
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sort);
        }

        try {
            menuCategory = Board.MenuCategory.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            if(type.equals("ALL")) {
                if(sort.equals("LIKE_LIST")) {
                    List<Long> likeBoardIds = findVerifiedBoardLike((String) authentication.getPrincipal());
                    return repository.findAllByBoardIdIn(likeBoardIds, pageable);
                }
                return repository.findAll(pageable);
            }
            throw new BusinessLogicException(ExceptionCode.INVALID_BOARD_FILTER_TYPE);
        }
        if(sort.equals("LIKE_LIST")) {
            List<Long> likeBoardIds = findVerifiedBoardLike((String) authentication.getPrincipal());
            return repository.findAllByMenuCategoryAndBoardIdIn(menuCategory, likeBoardIds, pageable);
        }
        return repository.findAllByMenuCategory(menuCategory, pageable);
    }

    public boolean likeBoard(long boardId,Authentication authentication) {
        Member member = extractMemberFromAuthentication(authentication, memberRepository);
        Board findBoard = repository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        if(findVerifiedBoardLike(member,findBoard)) {
            findBoard.setLikeCount(findBoard.getLikeCount() - 1);
            return false;
        }
        else {
            findBoard.setLikeCount(findBoard.getLikeCount() + 1);
            return true;
        }
    }

    private boolean findVerifiedBoardLike(Member member, Board board) {
        Optional<BoardLike> findBoardLike = boardLikeRepository.findByMemberAndBoard(member,board);
        if(findBoardLike.isPresent()){
            boardLikeRepository.delete(findBoardLike.get());
            return true;
        }else{
            BoardLike boardLike = new BoardLike();
            boardLike.setBoard(board);
            boardLike.setMember(member);
            boardLikeRepository.save(boardLike);
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<Long> findVerifiedBoardLike(String memberEmail) {
        List<BoardLike> findBoardLikes = boardLikeRepository.findByMember_email(memberEmail);
        return findBoardLikes.stream()
                .map(BoardLike::getBoardLikeId)  // BoardLike의 getId()를 사용해 PK(Long) 값을 추출
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean findVerifiedBoardLike(String memberEmail, Board board) {
        Optional<BoardLike> findBoardLike = boardLikeRepository.findByMember_emailAndBoard(memberEmail,board);
        return findBoardLike.isPresent();
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
