package com.jhs.crud.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupReq {

    @NotBlank(message = "Email은 필수 입력 값 입니다.")
    @Email
    private String email;

    @NotBlank(message = "PassWord는 필수 입력 값 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String pw;

    @NotBlank(message = "이름은 필수 입력 값 입니다.")
    private String name;
}
