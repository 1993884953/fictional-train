package com.example.demo.mapper;

import com.example.demo.model.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductMapper {
    @Update("update product set stock=stock-1 where id=#{id} and stock>0")
    int update(@Param("id")long id);

    @Select("select * from product ")
    List<Product> selectList();
}
