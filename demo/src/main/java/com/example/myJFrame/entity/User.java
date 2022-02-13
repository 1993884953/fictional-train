package com.example.myJFrame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private int uid;
    private String username;
    private String mobile;
    private String email;
    private String nickname;
    private String avatar;
    private int status;
    private String created_at;
    private String updated_at;
    private String avatar_url;
}
