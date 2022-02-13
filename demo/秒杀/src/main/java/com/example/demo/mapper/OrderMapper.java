package com.example.demo.mapper;

import com.example.demo.eneity.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper  {
    @Insert("INSERT INTO `order` (`order`,user_id,commodity_id,quantity,price) VALUES (#{order.order},#{order.userId},#{order.commodityId},#{order.quantity},#{order.price})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(@Param("order") Order order);

    @Select("SELECT * FROM `order` WHERE `order`=#{order}")
    Order find(Order order);
}
