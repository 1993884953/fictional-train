package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class OrderCreatedDto {
    private long id;
    private long userId;
    private long quantity;

    public OrderCreatedDto() {
        this.quantity =1;
    }

    public OrderCreatedDto(long id, long userId, Integer quantity) {
        this.id = id;
        this.userId = userId;
        this.quantity = quantity==null?1:quantity;
    }
}
