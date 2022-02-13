package com.example.demo.util;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//帖子对应的信息
public class PostResult<T> {
    private int userId;
    private String username;
    private String nickname;
    private String avatar;

    private Boolean like;
    private Boolean favorite;
    private int level;

    private T post;
}