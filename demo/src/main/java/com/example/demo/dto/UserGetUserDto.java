package com.example.demo.dto;
import lombok.Data;

@Data
public class UserGetUserDto {
    private int id;
    private String username;
    private String nickname;
    private String mobile;
    private String avatar;
    private String createdAt;
    private int level;
}
