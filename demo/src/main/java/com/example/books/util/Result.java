package com.example.books.util;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
//结果的模板类
public class Result<T> {
    private final String code;
    private final String message;
    private final T data;
}
