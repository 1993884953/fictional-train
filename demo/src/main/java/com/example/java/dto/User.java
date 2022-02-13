package com.example.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private int id;
    private int uid;
    private String username;
    private String mobile;
    private String email;
    private int status;
    private String created_at;
    private String updated_at;
    private String nickname;
    private String avatar;
    private String avatar_url;
}
