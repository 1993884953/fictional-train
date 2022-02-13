package com.example.java.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//发送消息的内容
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private String text;
}
