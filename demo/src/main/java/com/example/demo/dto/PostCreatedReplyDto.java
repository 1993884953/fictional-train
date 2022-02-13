package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PostCreatedReplyDto {
    @NotNull(message = "id不能为空")
    private Integer id;
    @NotBlank
    private String content;
}
