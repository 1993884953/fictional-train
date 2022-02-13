package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//产品库
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private int id;
    private String name;
    private String price;
    private int stock;//存货
}
