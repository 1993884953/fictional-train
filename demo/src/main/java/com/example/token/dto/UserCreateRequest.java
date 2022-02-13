package com.example.token.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
//接收用户创建信息
public class UserCreateRequest {
    private String id;
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp="^(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[89])([0-9]){8}$",message="手机格式不正确")
    private String mobile;
    @NotNull(message = "用户名不能为空")
    @Pattern(regexp="^[\u4e00-\u9fa5_a-zA-Z0-9]{4,10}$",message="用户名称只能输入中文字符 字母、数字，4-10个字符")
    private String nickname;
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}$", message = "密码需包含大小写字母、数字，6-20个字符")
    private String password;
    @NotNull(message = "请上传verificationKey")
    @Pattern(regexp="^([\u4e00-\u9fa5_a-zA-Z0-9]){32}$",message="验证密钥格式不正确")
    private String verificationKey;
    @Pattern(regexp="^([\u4e00-\u9fa50-9]){6}$",message="验证码格式不对")
    @NotNull(message = "请上传verificationCode")
    private String verificationCode;
    private String avatar;
}
