package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
    private int id;
    private String name;
    private int postId;
    private String createdAt;
    private String updateAt;
}
