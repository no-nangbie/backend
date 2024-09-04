package com.nonangbie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionCode {

    //member 관련
    MEMBER_NOT_FOUND(404,"Member Not Found"),
    MEMBER_EXISTS(409,"Member exists"),
    NICKNAME_EXISTS(409, "NickName exists");

    @Getter
    private int statusCode;

    @Getter
    private String statusDescription;
}