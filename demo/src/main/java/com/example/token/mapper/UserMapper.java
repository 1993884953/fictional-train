package com.example.token.mapper;

import com.example.token.dto.SmsCreateRequest;
import com.example.token.dto.UserCreateRequest;
import com.example.token.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//@Mapper
public interface UserMapper {
    @Insert("INSERT INTO user (mobile,nickname,password,avatar) VALUES (#{mobile},#{nickname},MD5(#{password}),#{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")//获取自增Id
    int userCreate(UserCreateRequest UserCreateRequest);
    //使用key查询code，手机号等信息
    @Select("SELECT * FROM user WHERE mobile=#{mobile}")
    SmsCreateRequest userIdFind(@Param("mobile") String mobile);
    //使用userId查询user表，返回user对象
    @Select("SELECT * FROM user WHERE id=#{id}")
    User userFind(@Param("id") String id);
    //登录获取userId
    @Select("SELECT user.id FROM user WHERE mobile=#{mobile} AND password=MD5(#{password})")
    String login(@Param("mobile") String mobile,@Param("password") String password);
}
