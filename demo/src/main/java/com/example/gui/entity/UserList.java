package com.example.gui.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// UserList.java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserList {

    private int id;
    private int uid;
    private String nickname;
    private String avatar;
    private String mobile;
    private String created_at;
    private String updated_at;
    private String avatar_url;
}
