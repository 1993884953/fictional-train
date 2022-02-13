package com.example.demo.mapper;

import com.example.demo.util.Pagination;
import com.example.demo.entity.Category;
import com.example.demo.entity.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {

    //查找所有分类
    @Select("SELECT * FROM category ORDER BY id ASC")
    List<Category> categories();

    //通过id
    @Select("SELECT name FROM category WHERE id=#{id}")
    String categoryId(@Param("id") int id);

    //创建帖子
    @Insert("INSERT INTO " +
            "post" +
            "(" + "title," + "parent_id,"+ "category_id," + "content," + "user_id," + "status" + ") " +
            "VALUES " +
            "(" + "#{title}," + "#{parentId},"+"#{categoryId},"+  "#{content}," + "#{userId}," + "#{status}" + ")")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Integer createPost(Post post);

    //删除帖子
    @Delete("DELETE FROM post WHERE (id=#{id} OR parent_id=#{id}) AND user_id=#{userId}")
    int deletePost(@Param("id") int id, @Param("userId") int userId);

    //更改帖子各种参数
    @Update("<script>" +
            "UPDATE post " +
            "<set>" +
            " favorite_count=#{favoriteCount} ," +
            " like_count=#{likeCount} ," +
            " view_count=#{viewCount} ," +
            " reply_count=#{replyCount} " +
            "</set>" +
            "<where>" +
            "id=#{id}" +
            "</where>" +
            "</script>")
    Integer updatePost(Post post);

    //返回帖子总数
    @Select("<script>" +
            "SELECT * FROM post" +
            "<where>" +
            "<if test='id !=0'>AND id=#{id} </if>" +
            "<if test='userId !=0'>AND user_id=#{userId} </if>" +
            "<if test='parentId !=0'>AND parent_id=#{parentId}</if>" +
            "</where>" +
            "</script>")
    List<Post> findPost(Post post);
    //1、父类ID为0
    //2、分类Id
    //3、标题
    //4、页面信息
    //5、时间限制、为空查询所有、否则查周榜或者月榜
    //6、帖子状态 、查询status为一、
    //7、userId存在的时候查自己创建的帖子
    //帖子推荐状态


    //8、根据 更新时间倒序排列


    @Select("<script>" +
            "SELECT *,view_count+reply_count*3+like_count*5+favorite_count*10 AS postHot " +
            "FROM post " +
            "<where>" +
            " AND parent_id=#{post.parentId} " +//父类Id，区别是帖子还是评论，不能省略
            "<if test='post.status !=0'>AND status=#{post.status} </if>" +//帖子状态、不能输出私人帖子

            "<if test='post.id !=0'>AND id=#{post.id} </if>" +//帖子Id 用于查找单个帖子的详情
            "<if test='post.userId !=0'>AND user_id=#{post.userId} </if>" +//userId,用于获取个人帖子
            "<if test='post.categoryId !=0'>AND category_id=#{post.categoryId} </if>" +//分类Id，用于分别分类
            "<if test='post.recommend !=0'>AND recommend=#{post.recommend} </if>" +//帖子的推荐状态

            "<if test='post.title !=null'>AND title LIKE CONCAT('%',#{post.title},'%') </if>" +//标题、模糊搜索
            "<if test='type !=0'> AND date(created_at) >= DATE_SUB(CURDATE(), INTERVAL #{type} DAY) </if>" +//查询最近时间

            "<if test='state ==1'>AND post.id IN (SELECT post_id FROM favorite WHERE favorite.type=2 AND favorite.user_id = #{post.userId})</if>" +//子查询收藏

            "</where>" +
            //在首页的时候根据更新时间倒序输出
            "<if test='type ==0'> ORDER BY update_at </if> " +
            //在周榜月榜的时候根据最近的热度，
            "<if test='type !=0'> GROUP BY id HAVING postHot >=1 ORDER BY postHot </if> " +
            //倒序输出5条记录
            "DESC LIMIT #{pagination.offset},#{pagination.limit} " +
            "</script>")
    List<Post> findListPost(@Param("post") Post post, @Param("pagination") Pagination pagination, @Param("type") int type, @Param("state") int state);

    @Select("<script>" +
            "SELECT COUNT(*) " +
            "FROM post " +
            "<where>" +
            " parent_id=#{post.parentId} " +//父类Id，区别是帖子还是评论，不能省略
            "<if test='post.status !=0'>AND status=#{post.status} </if>" +//帖子状态、不能输出私人帖子

            "<if test='post.id !=0'>AND id=#{post.id} </if>" +//帖子Id 用于查找单个帖子的详情
            "<if test='post.userId !=0'>AND user_id=#{post.userId} </if>" +//userId,用于获取个人帖子
            "<if test='post.categoryId !=0'>AND category_id=#{post.categoryId} </if>" +//分类Id，用于分别分类
            "<if test='post.recommend !=0'>AND recommend=#{post.recommend} </if>" +//帖子的推荐状态

            "<if test='post.title !=null'>AND title LIKE CONCAT('%',#{post.title},'%') </if>" +//标题、模糊搜索
            "<if test='type !=0'> AND date(update_at) >= DATE_SUB(CURDATE(), INTERVAL #{type} DAY) </if>" +//查询最近时间

            "<if test='state ==1'>AND post.id IN (SELECT post_id FROM favorite WHERE favorite.type=2 AND favorite.user_id = #{post.userId}) </if>" +//子查询收藏

            "<if test='type !=0'>AND view_count+reply_count*3+like_count*5+favorite_count*10 >=1 </if> " +
            "</where>" +
            //在周榜月榜的时候根据最近的热度，
//            "<if test='type !=0'> GROUP BY id HAVING view_count+reply_count*3+like_count*5+favorite_count*10 >=1 ORDER BY view_count+reply_count*3+like_count*5+favorite_count*10 </if> " +
            //倒序输出5条记录
            "</script>")
    long findListPostTotal(@Param("post") Post post, @Param("pagination") Pagination pagination, @Param("type") int type, @Param("state") int state);
}