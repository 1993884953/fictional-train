package com.example.demo.entity;

import lombok.Data;

@Data
public class Category {
    private int id;
    private String name;
    private int status;
    private  String createdAt;
    private  String updateAt;
}
