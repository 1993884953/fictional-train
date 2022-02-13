package com.example.token.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SmsCreateRequest {
    private String id;
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp="^(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[89])([0-9]){8}$",message="手机格式不正确")
    private String mobile;
    private String verificationKey;
    private String verificationCode;
    private String createdAt;
}

