package com.nonangbie.board.controller;

import com.nonangbie.S3.service.S3Uploader;
import com.nonangbie.auth.service.AuthService;
import com.nonangbie.board.dto.BoardDto;
import com.nonangbie.board.entity.Board;
import com.nonangbie.board.mapper.BoardMapper;
import com.nonangbie.board.service.BoardService;
import com.nonangbie.dto.MultiResponseDto;
import com.nonangbie.dto.SingleResponseDto;
import com.nonangbie.utils.ExtractMemberEmail;
import com.nonangbie.utils.UriCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/boards")
@Validated
@RequiredArgsConstructor
public class BoardController {
    private final static String BOARD_DEFAULT_URL = "/boards";
    private final BoardService service;
    private final BoardMapper mapper;

    @PostMapping("/menus")
    public ResponseEntity createMenu(
            @RequestParam("image") MultipartFile image) throws IOException {
        service.createMenu(image);
        System.out.println("hello");
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 게시글 추가 메서드
     *
     * @param requestBody : PostDto
     * @return Httpstatus 200
     * @Author 신민준
     */
    @PostMapping
    public ResponseEntity postBoard(@Valid @ModelAttribute BoardDto.Post requestBody,
                                    Authentication authentication) {
        MultipartFile imageFile = requestBody.getImageFile();
        Board createBoard = service.createBoard(mapper.boardPostDtoToBoard(requestBody),imageFile,authentication);
        URI location = UriCreator.createUri(BOARD_DEFAULT_URL, createBoard.getBoardId());

        return ResponseEntity.created(location).build();
    }

    /**
     * 게시글 수정 메서드
     *
     * @param boardId
     * @param requestBody
     * @return
     */
    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(@PathVariable("board-id") long boardId,
                                    @Valid @ModelAttribute BoardDto.Patch requestBody,
                                     Authentication authentication) {
        MultipartFile imageFile = requestBody.getImageFile();
        Board createBoard = service.updateBoard(mapper.boardpatchDtoToBoard(requestBody),imageFile,boardId,authentication);
        URI location = UriCreator.createUri(BOARD_DEFAULT_URL, createBoard.getBoardId());

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(@PathVariable("board-id") @Positive long boardId,
                                      Authentication authentication) {
        service.deleteBoard(boardId,authentication);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") @Positive long boardId,
                                   Authentication authentication) {
        Board findBoard = service.findVerifiedBoard(boardId,authentication);
        boolean likeCheck = service.findVerifiedBoardLike((String) authentication.getPrincipal(),findBoard);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.boardToBoardDtoResponse(findBoard,likeCheck)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getBoards(@RequestParam(name = "type", required = false, defaultValue = "ALL") String boardType,
                                    @RequestParam(name = "sort") String sort,
                                    @RequestParam(name = "keyword") String keyword,
                                    @Positive @RequestParam int page,
                                    @Positive @RequestParam int size,
                                    Authentication authentication) {

        Page<Board> pageBoards = service.findBoardsByType(boardType, sort, page - 1, size,keyword, authentication);
        List<Board> boards = pageBoards.getContent();
        if(sort.equals("LIKE_LIST")) {
            List<Long> likeBoardIds = service.findVerifiedBoardLike((String) authentication.getPrincipal());
            return new ResponseEntity<>(
                    new MultiResponseDto<>(mapper.boardToBoardDtoResponses(boards), pageBoards),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.boardToBoardDtoResponses(boards), pageBoards),
                HttpStatus.OK);
    }

    /**
     * 게시글 좋아요 메서드
     *
     * @param boardId
     * @return
     */
    @PostMapping("/{board-id}/like")
    public ResponseEntity likeBoard(@PathVariable("board-id") long boardId,
                                    Authentication authentication) {
        return service.likeBoard(boardId,authentication) ?
                new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
