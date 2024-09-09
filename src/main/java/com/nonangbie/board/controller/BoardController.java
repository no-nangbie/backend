package com.nonangbie.board.controller;

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

import javax.validation.Valid;
import javax.validation.constraints.Positive;
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
    private final AuthService authService;

    /**
     * 게시글 추가 메서드
     *
     * @param requestBody : PostDto
     * @return Httpstatus 200
     * @Author 신민준
     */
    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardDto.Post requestBody,
                                    Authentication authentication) {
        Board createBoard = service.createBoard(mapper.boardPostDtoToBoard(requestBody),authentication);
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
                                    @Valid @RequestBody BoardDto.Patch requestBody,
                                     Authentication authentication) {
        Board createBoard = service.updateBoard(mapper.boardpatchDtoToBoard(requestBody),boardId,authentication);
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
    public ResponseEntity getBoard(@PathVariable("board-id")
                                   @Positive long boardId,
                                   Authentication authentication) {
        Board findBoard = service.findVerifiedBoard(boardId,authentication);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.boardToBoardDtoResponse(findBoard)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getBoards(@RequestParam(name = "type") String boardType,
                                    @RequestParam(name = "sort") String sort,
                                    @Positive @RequestParam int page,
                                    @Positive @RequestParam int size,
                                    Authentication authentication) {

        Page<Board> pageBoards = service.findBoardsByType(boardType, sort, page - 1, size, authentication);
        List<Board> boards = pageBoards.getContent();

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
    @PostMapping("/{board-id}")
    public ResponseEntity likeBoard(@PathVariable("board-id") long boardId,Authentication authentication) {
        return service.likeBoard(boardId,authentication) ?
                new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
