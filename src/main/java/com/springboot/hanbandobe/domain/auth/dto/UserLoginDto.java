package com.springboot.hanbandobe.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@ToString(exclude = "password")
@Data
public class UserLoginDto {

    @Email(message = "유효한 이메일 형식을 입력해주세요.")
    @NotBlank(message = "이메일 입력을 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호 입력을 필수입니다.")
    private String password;
}
