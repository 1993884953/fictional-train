package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenCreateDto {
    @NotNull(message = "用户名不能为空")
    @Pattern(regexp="^[\u4e00-\u9fa5_a-zA-Z0-9]{4,10}$",message="用户名称只能输入中文字符 字母、数字，4-10个字符")
    private String username;
    @NotNull(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}$", message = "密码需包含大小写字母、数字，6-20个字符")
    private String password;

}
