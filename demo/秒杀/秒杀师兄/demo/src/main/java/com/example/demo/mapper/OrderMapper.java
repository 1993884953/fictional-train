package com.example.demo.mapper;

import com.example.demo.model.Order;
import com.example.demo.model.Product;
import org.apache.ibatis.annotations.Insert;

public interface OrderMapper {
    //添加订单
    @Insert("insert into `order` (id,product_id,name) value (#{id},#{productId},#{name})")
    void insert(Order order);


}
