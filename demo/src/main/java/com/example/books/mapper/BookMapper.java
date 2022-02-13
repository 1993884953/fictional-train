package com.example.books.mapper;

import com.example.books.dto.Pagination;
import com.example.books.entity.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {

    //    //复杂查询
    @Select("<script>" +
            "SELECT * FROM books " +
            "<where>" +
            "<if test='book.name !=null'> AND name LIKE CONCAT('%',#{book.name},'%') </if>" +
            "<if test='book.id !=0'>AND id=#{book.id}</if>" +
            "</where>" +
            "ORDER BY id DESC " +
            "LIMIT #{pagination.offset},#{pagination.limit}" +
            "</script>")
    List<Book> findBooks(@Param("book") Book book, @Param("pagination") Pagination pagination);

    @Select(
            "<script>" +
            "SELECT * FROM book WHERE id in" +
            " <foreach collection='books' item='item' index='index' " +
                    "open='(' separator=',' close=')'> " +
                    "#{item}</foreach>" +
            "</script>"
    )
    List<Book> findBookList(List<Book> books);

    //统计条数
    @Select("<script>" +
            "SELECT COUNT(*) FROM books" +
            "<where>" +
            "<if test='book.name !=null'> AND name LIKE CONCAT('%',#{book.name},'%') </if>" +
            "<if test='book.id !=0'>AND id=#{book.id}</if>" +
            "</where>" +
            "</script>")
    long count(@Param("book") Book book);


    //查找所有图书
    @Select("SELECT * FROM books")
    List<Book> findAllBook();

    //查询一本图书信息
    @Select("SELECT * FROM books WHERE id=#{id} ")
    Book findIdBook(@Param("id") String id);

    //新增图书,自主获取并且更改主键
    @Insert("INSERT INTO books (name,`describe`,`price`)VALUES (#{name},#{describe},#{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertBook(Book book);

    //更改图书，返回受影响行数
    @Update("UPDATE books SET name=#{book.name},`describe`=#{book.describe},price=#{book.price} WHERE id =#{id}")
    int update(@Param("id") String id, @Param("book") Book book);

    //删除图书，返回受影响行数
    @Delete("DELETE FROM books WHERE id=#{id}")
    int delete(@Param("id") String id);
}
