package com.example.demo.mapper;

import com.example.demo.dto.PostGetTagOrderDto;
import com.example.demo.entity.Post;
import com.example.demo.entity.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TagMapper {
    //    @Insert(
//            "<script>" +
//                    "SELECT * FROM book WHERE id in" +
//                    " <foreach collection='tagList' item='item' index='index' " +
//                    "open='(' separator=',' close=')'> " +
//                    "#{item.id}</foreach>" +
//            "</script>"
//    )
//    List<Book> findBookList(List<Tag> tagList);

    @Insert("<script>" +
            "INSERT INTO tag " +
            "(name,post_id) VALUES " +
            "<foreach collection='tagList' item='item' separator=','>" +
            "(#{item.name},#{item.postId})" +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int create(@Param("tagList") List<Tag> tagList);
    //返回热门标签
    @Select("SELECT " +
            "MIN(id) AS id,name,COUNT(name) AS quantity,MIN(created_at) AS created_at,MIN(update_at) AS update_at " +
            "FROM tag " +
            "GROUP BY name " +
            "ORDER BY quantity DESC LIMIT #{hotTagNumber}")
    List<PostGetTagOrderDto>getTagOrder(@Param("hotTagNumber")int hotTagNumber);
    //返回单个帖子的标签
    @Select("SELECT " +
            "*" +
            "FROM tag " +
            "WHERE post_id=#{id}")
    List<Tag>getPostTag(Post post);
}