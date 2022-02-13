package com.example.books.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//接收类
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {
private String name;
private String describe;
private String price;
}
