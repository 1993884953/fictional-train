package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
    private int id;
    private int userId;
    private int postId;
    private int type;

    private  String createdAt;
    private  String updateAt;
}
