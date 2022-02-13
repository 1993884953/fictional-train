package com.example.demo.controller;

import com.example.demo.util.Pagination;
import com.example.demo.dto.*;
import com.example.demo.entity.Category;
import com.example.demo.entity.Post;
import com.example.demo.interceptor.UserContext;
import com.example.demo.service.PostService;
import com.example.demo.util.JsonResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
public class PostController {
    @Resource
    PostService postService;

    //获取多个分类Id
    @GetMapping("/api/post/categories")
    public JsonResult<List<Category>> categories() {
        List<Category> data = postService.categories();
        return new JsonResult<>("分类获取成功", data);
    }

    //获取单个分类Id
    @GetMapping("/api/post/categoryId")
    public JsonResult<String> categoryId(Integer id) {
        if (id == null) {
            throw new RuntimeException("未上传id");
        }
        return new JsonResult<>("SUCCESS", "分类获取成功", postService.categoryId(id));
    }

    //创建帖子
    @PostMapping("/api/post/create")
    public JsonResult<Map<String, Post>> createPost(@Valid PostCreatePostDto postCreatePostDto) {

        boolean isMatch = Pattern.matches("^([\\s])+$", postCreatePostDto.getTag());
        if (Objects.equals(postCreatePostDto.getTag(), "") || isMatch) {
            postCreatePostDto.setTag(null);
        }
        return new JsonResult<>("帖子创建成功", postService.createPost(postCreatePostDto));
    }

    //创建评论
    @PostMapping("/api/post/reply")
    public JsonResult<Map<String, Post>> createdReply(@Valid PostCreatedReplyDto postCreatedReplyDto) {
        return new JsonResult<>("回复成功", postService.createdReply(postCreatedReplyDto));
    }

    //删除帖子
    @PostMapping("/api/post/delete")
    public JsonResult<Map<String, String>> deletePost(Integer id) {
        if (id == null || id == 0) {
            throw new RuntimeException("删除失败,未上传id");
        }
        return new JsonResult<>("删除成功", postService.deletePost(id));
    }

    //收藏帖子
    @PostMapping("/api/post/favorite")
    public JsonResult<Map<String, Object>> favorite(Integer id) {
        if (id == null) {
            throw new RuntimeException("收藏失败,未上传id");
        }
        PostFavoriteDto postFavoriteDto = PostFavoriteDto.builder()
                .postId(id)
                .userId(UserContext.getUser().getId())
                .type(PostFavoriteDto.TYPE_FAVORITE).build();
        return new JsonResult<>("操作成功", postService.favorite(postFavoriteDto));
    }

    //帖子点赞
    @PostMapping("/api/post/like")
    public JsonResult<Map<String, Object>> like(Integer id) {
        if (id == null) {
            throw new RuntimeException("点赞失败,未上传id");
        }
        PostFavoriteDto postFavoriteDto = PostFavoriteDto.builder()
                .postId(id)
                .userId(UserContext.getUser().getId())
                .type(PostFavoriteDto.TYPE_LIKE).build();
        return new JsonResult<>("操作成功", postService.favorite(postFavoriteDto));
    }

    //获取热门标签
    @GetMapping("/api/post/getTagOrder")
    public JsonResult<List<PostGetTagOrderDto>> getTagOrder(Integer hotTagNumber) {
        if (hotTagNumber == null) {
            hotTagNumber = 5;
        }
        List<PostGetTagOrderDto> data = postService.getTagOrder(hotTagNumber);
        return new JsonResult<>("获取数据成功", data);
    }

    //获取帖子详情
    @GetMapping("/api/post/view")
    public JsonResult<Map<String, Object>> view(Integer id) {
        System.out.println(id);
        if (id == null) {
            throw new RuntimeException("帖子不存在或者已经被删除");
        }
        Map<String, Object> data = postService.view(id);
        return new JsonResult<>("获取成功", data);
    }

    //查询首页
    @GetMapping("/api/post/list")
    public JsonResult<Map<String, Object>> listPost(@Valid PostSearchRequestDto postSearchRequestDto, Pagination pagination, String token) {
        System.out.println(postSearchRequestDto);
//        postService.listPost(postSearchRequestDto,pagination,Post.TYPE_WEEK);

        return new JsonResult<>("获取成功", postService.listPost(postSearchRequestDto, pagination, 0, token));
    }    //查询首页

    //获取月榜
    @GetMapping("/api/post/month")
    public JsonResult<Map<String, Object>> month(@Valid PostSearchRequestDto postSearchRequestDto, Pagination pagination, String token) {
        System.out.println(postSearchRequestDto);
//        postService.listPost(postSearchRequestDto,pagination,Post.TYPE_WEEK);


        return new JsonResult<>("获取成功", postService.listPost(postSearchRequestDto, pagination, Post.TYPE_MOUTH, token));
    }

    //周榜
    @GetMapping("/api/post/week")
    public JsonResult<Map<String, Object>> week(@Valid PostSearchRequestDto postSearchRequestDto, Pagination pagination, String token) {
        System.out.println(postSearchRequestDto);
//        postService.listPost(postSearchRequestDto,pagination,Post.TYPE_WEEK);


        return new JsonResult<>("获取成功", postService.listPost(postSearchRequestDto, pagination, Post.TYPE_WEEK, token));
    }

    @GetMapping("/api/post/recommend")
    public JsonResult<Map<String, Object>> recommend(@Valid PostSearchRequestDto postSearchRequestDto, Pagination pagination, String token) {
        System.out.println(postSearchRequestDto);

        return new JsonResult<>("获取成功", postService.listPost(postSearchRequestDto, pagination, 0, token));

    }

    //我的帖子
    @GetMapping("/api/post/my")
    public JsonResult<Map<String, Object>> myPost(@Valid PostSearchRequestDto postSearchRequestDto, Pagination pagination, String token) {
        System.out.println(postSearchRequestDto);
//        postService.listPost(postSearchRequestDto,pagination,Post.TYPE_WEEK);
        postSearchRequestDto.setUserId(UserContext.getUser().getId());
        return new JsonResult<>("获取成功", postService.listPost(postSearchRequestDto, pagination, 0, token));
    }

    @GetMapping("/api/post/myFavorite")
    public JsonResult<Map<String, Object>> myFavorite(@Valid PostSearchRequestDto postSearchRequestDto, Pagination pagination, String token) {
        System.out.println(postSearchRequestDto);
//        postService.listPost(postSearchRequestDto,pagination,Post.TYPE_WEEK);
        postSearchRequestDto.setState(Post.STATE_FAVORITE);
        postSearchRequestDto.setUserId(UserContext.getUser().getId());
        return new JsonResult<>("获取成功", postService.listPost(postSearchRequestDto, pagination, 0, token));
    }

    @GetMapping("/api/post/replyList")
    public JsonResult<Map<String, Object>> viewReplies(int id, Pagination pagination, String token) {

        if (id == 0) {
            throw new RuntimeException("请上传帖子Id");
        }
    PostSearchRequestDto postSearchRequestDto = PostSearchRequestDto.builder().parentId(id).build();

        return new JsonResult<>("获取成功", postService.listPost(postSearchRequestDto, pagination, 0, token));
    }
}
