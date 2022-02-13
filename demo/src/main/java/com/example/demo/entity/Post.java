package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post{
    //获取一个月或者一周的时间
    //public static final String TYPE_MOUTH=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date((new java.util.Date().getTime() - (long)30*24*60*60*1000)));
    //public static final String TYPE_WEEK=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date((new java.util.Date().getTime() - (long)7*24*60*60*1000)));
    public static final int TYPE_WEEK=7;
    public static final int TYPE_MOUTH=30;

    public static final int STATE_FAVORITE=1;

    private int id;
    private int parentId;
    private int userId;
    private String title;
    private String content;
    //帖子状态
    private int status;
    private  String createdAt;
    private  String updateAt;
    //分类与分类id
    private int categoryId;
    private String category;
    //推荐状态
    private int recommend;
    //回复数，浏览数
    private int replyCount;
    private int viewCount;
    //点赞数，收藏数
    private int likeCount;
    private int favoriteCount;
    private int postHot;
}
