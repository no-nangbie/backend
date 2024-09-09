package com.nonangbie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionCode {

    //board 관련
    BOARD_NOT_FOUND(404,"Board Not Found"),
    BOARD_EXISTS(409,"Board exists"),
    INVALID_BOARD_FILTER_TYPE(400, "Invalid Board Filter Type"),

    //member 관련
    MEMBER_NOT_FOUND(404,"Member Not Found"),
    MEMBER_EXISTS(409,"Member exists"),
    NICKNAME_EXISTS(409, "NickName exists"),

    //menu 관련
    MENU_NOT_FOUND(404, "Menu Not Found"),

    //food 관련
    FOOD_NOT_FOUND(404, "Food Not Found"),
    FOOD_MENU_NOT_FOUND(404, "Food Menu Not Found"),

    //메일 전송 관련
    UNABLE_TO_SEND_EMAIL(404, "이메일 전송에 실패했습니다."),
    NO_SUCH_ALGORITHM(404, "No_Such_Algorithm"),

    //Access
    ACCESS_DENIED(403,"Access denied"),

    MEMBER_FOOD_NOT_FOUND(404, "Member Food Not Found"),

    //토큰 인증 관련
    UNAUTHORIZED_MEMBER(401, "권한이 없는 멤버입니다."),
    TOKEN_INVALID(404, "토큰값이 유효하지 않습니다.");



    @Getter
    private int statusCode;

    @Getter
    private String statusDescription;
}