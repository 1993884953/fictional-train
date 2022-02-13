package com.example.token.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TokenMapper {
    //创建token
    @Insert("INSERT INTO token (user_id,token) VALUES (#{userId},#{token})")
    int tokenCreate(@Param("userId") String userId, @Param("token") String token);
    //token查找userId
    @Select("SELECT user_id FROM token WHERE token=#{token} ")
    String tokenFind(@Param("token") String token);
}
