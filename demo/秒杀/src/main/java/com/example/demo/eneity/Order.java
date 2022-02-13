package com.example.demo.eneity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements Serializable {
    private long  id;
    private  String order;
    private long userId;
    private long commodityId;
    private long quantity;
    private long price;
    private String createdAt;
    private String updatedAt;
}
