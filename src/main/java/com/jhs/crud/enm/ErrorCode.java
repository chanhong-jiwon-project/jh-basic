package com.jhs.crud.enm;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorCode {
    //토큰 익셉션 처리
    WRONG_TYPE_TOKEN(401, "잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN(401, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_TOKEN(401, "지원되지 않는 JWT 토큰입니다."),
    WRONG_TOKEN(401, "JWT 토큰이 잘못되었습니다."),
    TOKEN_NULL(401, "JWT 토큰이 없습니다."),

    //회원가입&로그인
    ALREADY_EMAIL(1000,"이미 존재하는 Email입니다");


    private final Integer code;
    private final String message;

    public String getErrorMessage() {
        return message;
    }

    public int getErrorCode() {
        return code;
    }

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    
}
