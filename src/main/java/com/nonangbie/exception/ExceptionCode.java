package com.nonangbie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionCode {

    //member 관련
    MEMBER_NOT_FOUND(404,"Member Not Found"),
    MEMBER_EXISTS(409,"Member exists"),
    NICKNAME_EXISTS(409, "NickName exists"),

    //menu 관련
    MENU_NOT_FOUND(404, "Menu Not Found"),

    //food 관련
    FOOD_NOT_FOUND(404, "Food Not Found"),

    FOOD_MENU_NOT_FOUND(404, "Food Menu Not Found"),;



    @Getter
    private int statusCode;

    @Getter
    private String statusDescription;
}