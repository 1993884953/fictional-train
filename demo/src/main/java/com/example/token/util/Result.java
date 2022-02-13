package com.example.token.util;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
//@AllArgsConstructor
@Builder
@AllArgsConstructor
//结果的模板类
public class Result<T> {
    private final String code;
    private final String message;
    private final T data;

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}