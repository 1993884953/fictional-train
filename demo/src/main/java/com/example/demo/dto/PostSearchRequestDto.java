package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSearchRequestDto {
    private int id;//单个帖子
    private int userId;//userId,用于获取个人帖子
    private int status;
    private int parentId;//父类Id，区别是帖子还是评论，不能省略
    private int categoryId;//分类Id，用于分别分类
    private int recommend;//帖子的推荐状态
    private String title;//标题、模糊搜索

    private int state;   //区分收藏

    private int page;
    private int limit;
    private long total;
}
//"postDtoList": [
//      {
//        "userId": 0,
//        "username": "string"
//        "avatar": "string",
//        "favorite": true,
//        "level": 0,
//        "like": true,
//        "nickname": "string",
//        "post": {
//          "category": "string",
//          "categoryId": 0,
//          "content": "string",
//          "createdAt": "2021-12-25T08:06:34.095Z",
//          "favoriteCount": 0,
//          "id": 0,
//          "likeCount": 0,
//          "parentId": 0,
//          "recommend": 0,
//          "replyCount": 0,
//          "status": 0,
//          "title": "string",
//          "updatedAt": "2021-12-25T08:06:34.095Z",
//          "userId": 0,
//          "viewCount": 0
//        },
//      }
//    "postSearchDto": {
//      "categoryId": 0,
//      "createdBegin": "2021-12-25T08:06:34.095Z",
//      "createdEnd": "2021-12-25T08:06:34.095Z",
//      "ids": [
//        0
//      ],
//      "limit": 0,
//      "page": 0,
//      "recommend": 0,
//      "status": 0,
//      "tag": "string",
//      "title": "string",
//      "total": 0,
//      "userId": 0,
//      "username": "string"
//    }