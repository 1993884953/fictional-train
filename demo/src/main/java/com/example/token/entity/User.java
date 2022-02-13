package com.example.token.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String mobile;
    private String nickname;
    private String avatar;
}