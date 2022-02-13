package com.example.books;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication
@MapperScan("com.example.books.mapper") //配置接口目录文件，方便框架查询接口@MapperScan("com.example.books.mapper") //配置接口目录文件，方便框架查询接口
public class BooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);
    }

}
