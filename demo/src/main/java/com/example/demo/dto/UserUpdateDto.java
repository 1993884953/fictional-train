package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//用户数据更新类
public class UserUpdateDto {
    private int id;
//    @Pattern(regexp="^(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[89])([0-9]){8}$",message="手机格式不正确")
    private String mobile;
    @Pattern(regexp="^[\u4e00-\u9fa5_a-zA-Z0-9]{4,10}$",message="用户名称只能输入中文字符 字母、数字，4-10个字符")
    private String nickname;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}$", message = "密码需包含大小写字母、数字，6-20个字符")
    private String password;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}$", message = "旧密码需包含大小写字母、数字，6-20个字符")
    private String oldPassword;
    private String updateAt;
}
