package com.example.demo.mapper;

import com.example.demo.entity.Favorite;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FavoriteMapper {
    //收藏与点赞更新
    @Update("UPDATE favorite SET likes=#{likes},favorite=#{favorite} WHERE post_id=#{postId} AND user_id=#{userId}")
    Integer update(Favorite favorite);
    //统计点赞与收藏数量
    @Select("<script>"+
            "SELECT * FROM favorite " +
            " <where>" +
            " post_id=#{postId} " +
            " AND type=#{type} " +
            " <if test='userId !=0'>AND user_id=#{userId}</if> " +
            " </where>" +
            "</script>")
    List<Favorite> findFavoriteByPostId(Favorite favorite);


    //收藏与点赞创建
    @Insert("INSERT INTO favorite (user_id,post_id,type) VALUES (#{userId},#{postId},#{type});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer create(Favorite favorite);

    @Delete("<script>"+
            " DELETE FROM favorite " +
            " <where>" +
            " post_id=#{postId} " +
            " <if test='userId !=0'>AND user_id=#{userId}</if> " +
            " <if test='type !=0'>AND type=#{type}</if> " +
            " </where>" +
            "</script>")
    Integer delete(Favorite favorite);
}
