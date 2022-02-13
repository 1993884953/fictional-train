package com.example.demo.mapper;

import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TokenMapper {
    //创建token
    @Insert("INSERT INTO token (user_id,token) VALUES (#{userId},#{token})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int create(Token token);
    //token查找userId
    @Select("SELECT user_id FROM token WHERE token=#{token}")
    Integer find(String token);
    @Delete("DELETE FROM token WHERE user_id=#{userId}")
    int delete(@Param("userId") int userId);
}
