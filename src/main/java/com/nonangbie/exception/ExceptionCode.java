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

    //Access
    ACCESS_DENIED(403,"Access denied");



    @Getter
    private int statusCode;

    @Getter
    private String statusDescription;
}