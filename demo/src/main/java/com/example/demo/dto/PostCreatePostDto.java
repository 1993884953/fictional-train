package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreatePostDto {
    private int categoryId;
    @NotNull(message = "标题不能为空")
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotNull(message = "内容不能为空")
    @NotBlank(message = "标题不能为空")
    private String content;
//    @NotEmpty(message = "标签不能为单个空格")
    @Pattern(regexp = "^[\u4e00-\u9fa5\\s*_a-zA-Z0-9]{0,30}$", message = "标签只能输入中文字符 字母、数字，1~30个字符，")
    private String tag;
    private int status;
}