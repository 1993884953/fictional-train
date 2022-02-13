package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//生成的订单
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    private int id;
    private long productId;
    private String name;
}
