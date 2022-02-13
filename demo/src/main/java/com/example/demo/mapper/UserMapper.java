package com.example.demo.mapper;

import com.example.demo.dto.UserGetVisitorDto;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    //插入一条user数据
    @Insert("INSERT INTO user (username,nickname,password,avatar) VALUES (#{username},#{nickname},MD5(#{password}),#{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
//获取自增Id
    int create(User user);

    //使用userId和username查询user表，返回user对象
    @Select("<script>" +
            "SELECT * FROM user" +
            "<where>" +
            "<if test='id !=0'>AND id=#{id}</if>" +
            "<if test='username !=null'> AND username=#{username} </if>" +
            "<if test='password !=null'> AND password=MD5(#{password})</if>" +
            "</where>" +
            "</script>")
User find(User user);

    //使用userId和username查询user表，返回user对象
    @Update("<script>" +
            "UPDATE user " +
            "<set>" +
            "<if test='mobile !=null'> mobile=#{mobile},</if>" +
            "<if test='nickname !=null'> username=#{nickname},</if>" +
            "<if test='password !=null'> password=MD5(#{password}),</if>" +
            "<if test='updateAt !=null'> update_at=#{updateAt},</if>" +
            "<if test='avatar !=null'> avatar=#{avatar},</if>" +
            "</set>" +
            "<where>" +
            "id=#{id}" +
            "</where>" +
            "</script>")
    int update(User user);
    //多表联查最近访客
    @Select("SELECT " +
            "user.avatar,user.username " +
            "FROM user " +
            "LEFT JOIN token " +
            "ON token.user_id=user.id ORDER BY token.id DESC LIMIT 5")
    List<UserGetVisitorDto> getVisitor();
}
