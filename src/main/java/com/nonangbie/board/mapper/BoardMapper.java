package com.nonangbie.board.mapper;

import com.nonangbie.board.dto.BoardDto;
import com.nonangbie.board.entity.Board;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardPostDtoToBoard(BoardDto.Post requestBody);
    Board boardpatchDtoToBoard(BoardDto.Patch requestBody);

    default BoardDto.Response boardToBoardDtoResponse(Board board) {
        BoardDto.Response response = new BoardDto.Response();
        response.setMenuCategory(board.getMenuCategory().getStatus());
        response.setDifficulty(board.getDifficulty().getStatus());
        response.setBoardId(board.getBoardId());
        response.setAuthor(board.getMember().getNickname());
        response.setTitle(board.getTitle());
        response.setBoardContent(board.getBoardContent());
        response.setFoodContent(board.getFoodContent());
        response.setRecipeContent(board.getRecipeContent());
        response.setImageUrl(board.getImageUrl());
        response.setCookingTime(board.getCookingTime());
        response.setServingSize(board.getServingSize());
        response.setLikesCount(board.getLikeCount());
        response.setLikesCount(board.getLikeCount());

        return response;
    }

    default List<BoardDto.Responses> boardToBoardDtoResponses(List<Board> boards) {
        return boards
                .stream()
                .map(board -> BoardDto.Responses
                        .builder()
                        .boardId(board.getBoardId())
                        .title(board.getTitle())
                        .author(board.getMember().getNickname())
                        .imageUrl(board.getImageUrl())
                        .menuCategory(board.getMenuCategory().getStatus())
                        .cookingTime(board.getCookingTime())
                        .servingSize(board.getServingSize())
                        .likesCount(board.getLikeCount())
                        .build())
                .collect(Collectors.toList());
    }
}
