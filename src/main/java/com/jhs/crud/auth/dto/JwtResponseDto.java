package com.jhs.crud.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JwtResponseDto {

    private final String token;
    private final String refreshtoken;
}
