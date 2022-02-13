package com.example.gui.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private int uid;
    private String username;
    private String nickname;
    private String mobile;
    private String avatar;

    private String email;
    private String avatar_url;
}
