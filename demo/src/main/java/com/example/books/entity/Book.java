package com.example.books.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//全参构造方法
@NoArgsConstructor//空构造方法
@Builder//链式调用
public class Book {
    private int id;
    private String isbn;
    private String name;
    private String describe;
    private String price;
    private String status;
    private String createdAt;
}
