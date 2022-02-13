package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    private int id;
    private int userId;
    private String token;
    private String createdAt;
    private String updateAt;
}
