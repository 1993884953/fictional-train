package com.example.demo.service;

import com.example.demo.util.Pagination;
import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.interceptor.UserContext;
import com.example.demo.mapper.FavoriteMapper;
import com.example.demo.mapper.PostMapper;
import com.example.demo.mapper.TagMapper;
import com.example.demo.mapper.TokenMapper;
import com.example.demo.util.PostResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class PostService {
    @Resource
    PostMapper postMapper;
    @Resource
    FavoriteMapper favoriteMapper;
    @Resource
    TagMapper tagMapper;
    @Resource
    UserService userService;
    @Resource
    TokenMapper tokenMapper;

    //分类标签
    public List<Category> categories() {
        List<Category> data = new ArrayList<>();
        postMapper.categories().forEach(item -> {
            System.out.println(item);
            Category category = new Category();
            BeanUtils.copyProperties(item, category);
            data.add(category);
        });
        return data;
    }

    //查询单个分类名
    public String categoryId(int id) {
        String name = postMapper.categoryId(id);
        if (name == null) {
            throw new RuntimeException("无此Id");
        }
        return name;
    }

    //创建帖子
    public Map<String, Post> createPost(PostCreatePostDto postCreatePostDto) {
        Post post = Post.builder().userId(UserContext.getUser().getId()).build();
        BeanUtils.copyProperties(postCreatePostDto, post);
        System.out.println("创建");
        if (postMapper.createPost(post) != 1) {
            throw new RuntimeException("创建失败");
        }

        Map<String, Post> data = new HashMap<>();
        data.put("post", post);

        //创建标签表
        System.out.println(postCreatePostDto);
        if (postCreatePostDto.getTag() != null) {
            //去除重复标签
            Set<String> tags = new HashSet<>(Arrays.asList(postCreatePostDto.getTag().split(" ")));
            List<Tag> tagList = new ArrayList<>();
            //循环插入数据库
            tags.forEach(item -> {
                if (!Objects.equals(item, "")) {
                    tagList.add(Tag.builder().name(item).postId(post.getId()).build());
                }
            });
            System.out.println("创建了" + tagMapper.create(tagList) + "条标签");
        }
        return data;
    }

    //创建评论
    public Map<String, Post> createdReply(PostCreatedReplyDto postCreatedReplyDto) {
        System.out.println(postCreatedReplyDto);



        List<Post> postList = postMapper.findPost(Post.builder().id(postCreatedReplyDto.getId()).build());
        System.out.println(postList);



                if (postList.size() == 0) {
            throw new RuntimeException("帖子不存在或者已经被删除");
        }
        //创建评论
        Post post = Post.builder()
                .content(postCreatedReplyDto.getContent())
                .parentId(postCreatedReplyDto.getId())
                .userId(UserContext.getUser().getId()).build();
        if (postMapper.createPost(post) != 1) {
            throw new RuntimeException("创建失败");
        }

        //修改回复数
        Post updatePost =postMapper.findPost( Post.builder()
                .id(postCreatedReplyDto.getId())
                .build()).get(0);
        updatePost.setReplyCount(updatePost.getReplyCount() + 1);

        if (postMapper.updatePost(updatePost) != 1) {
            throw new RuntimeException("回复数更新失败");
        }
        Map<String, Post> data = new HashMap<>();
        data.put("data", post);
        return data;
    }

    //删除帖子
    public Map<String, String> deletePost(int id) {
        //删除评论表
        int nums = postMapper.deletePost(id, UserContext.getUser().getId());
        if (nums == 0) {
            throw new RuntimeException("无此Id");
        }
        //删除点赞表
        Favorite favorite = Favorite.builder().postId(id).build();
        favoriteMapper.delete(favorite);

        Map<String, String> data = new HashMap<>();
        data.put("data", "共删除1篇帖子" + (nums - 1) + "条评论");
        return data;
    }

    //收藏帖子，喜欢帖子
    public Map<String, Object> favorite(PostFavoriteDto postFavoriteDto) {
        //查询帖子是否存在
        Post post = Post.builder().id(postFavoriteDto.getPostId()).build();
        List<Post>postList=postMapper.findPost(post);

        if (postList.size() != 1) {
            throw new RuntimeException("帖子不存在或者已经被删除");
        }


        //查询点赞表有无此数据
        Favorite favorite = new Favorite();
        BeanUtils.copyProperties(postFavoriteDto, favorite);
        List<Favorite> favoriteList = favoriteMapper.findFavoriteByPostId(favorite);


        //无数据，创建数据,增加
        BeanUtils.copyProperties(postList.get(0),post);


        if (favoriteList.size() == 0) {
            favoriteMapper.create(favorite);
            if (favorite.getType()==PostFavoriteDto.TYPE_FAVORITE){
                post.setFavoriteCount(postList.get(0).getFavoriteCount()+1);
            };
            if (favorite.getType()==PostFavoriteDto.TYPE_LIKE){
                post.setLikeCount(postList.get(0).getLikeCount()+1);
            };
        }
        //有数据，删除数据,降低
        if (favoriteList.size() != 0) {
            favoriteMapper.delete(favorite);
            if (favorite.getType()==PostFavoriteDto.TYPE_FAVORITE){
                post.setFavoriteCount(postList.get(0).getFavoriteCount()-1);
            };
            if (favorite.getType()==PostFavoriteDto.TYPE_LIKE){
                post.setLikeCount(postList.get(0).getLikeCount()-1);

            };
        }
        if(postMapper.updatePost(post)!=1){
            throw new RuntimeException("操作失败");
        }
        Map<String, Object> data = new HashMap<>();

        if (postFavoriteDto.getType() == 1) {
            data.put("likeBool", favoriteList.size() == 0);
            data.put("likeCount", post.getLikeCount());
        }
        if (postFavoriteDto.getType() == 2) {
            data.put("favoriteBool", favoriteList.size() == 0);
            data.put("favoriteCount", post.getFavoriteCount() );
        }

        return data;
    }

    //修改帖子数据
    public Map<String, Object> updatePost(Post post) {
        Integer nums = postMapper.updatePost(post);
        if (nums != 1) {
            throw new RuntimeException("更改错误");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("data", "更新了" + nums + "条数据");
        return data;
    }

    //获取热门标签
    public List<PostGetTagOrderDto> getTagOrder(int hotTagNumber) {
        return tagMapper.getTagOrder(hotTagNumber);
    }

    //获取帖子详情
    public Map<String, Object> view(int postId) {
        Post post = Post.builder().id(postId).build();

        List<Post> postList = postMapper.findPost(post);
        if (postList.size() != 1) {
            throw new RuntimeException("帖子不存在，或者已经被删除");
        }

        BeanUtils.copyProperties(postList.get(0), post);

        Favorite favorite = Favorite.builder()
                .userId(UserContext.getUser().getId())
                .postId(post.getId())
                .type(PostFavoriteDto.TYPE_LIKE).build();
        //设置like与收藏状态
        boolean likeState = favoriteMapper.findFavoriteByPostId(favorite).size() == 1;
        favorite.setType(PostFavoriteDto.TYPE_FAVORITE);
        boolean favoriteState = favoriteMapper.findFavoriteByPostId(favorite).size() == 1;
            User user = User.builder().id(post.getUserId()).build();
            UserGetUserDto userGetUserDto=new UserGetUserDto();
            BeanUtils.copyProperties(userService.getUserById(user), userGetUserDto);
        System.out.println(post);
                    //修改浏览量
            post.setViewCount(post.getViewCount() + 1);

            if (postMapper.updatePost(post)!=1) {
                System.out.println("浏览数修改失败");
            }
//
                List<Tag> tagList = tagMapper.getPostTag(post);
        Map<String, Object> data = new HashMap<>();

        data.put("favorite",favoriteState);
        data.put("like",likeState);
            PostResult<Post> postResult = PostResult.<Post>builder()
                    .like(likeState)
                    .userId(post.getUserId())
                    .favorite(favoriteState)
                    .post(post)
                    .build();
            BeanUtils.copyProperties(userGetUserDto, postResult);

        data.put("postDto", postResult);
        data.put("tagList", tagList);
        return data;
//return null;
//        List<PostResult<Post>> postResultList = new ArrayList<>();
//        //循环添加每一条帖子的数据
//        postList.forEach(item -> {
//            Favorite favorite = Favorite.builder()
//                    .userId(UserContext.getUser().getId())
//                    .postId(item.getId())
//                    .type(PostFavoriteDto.TYPE_LIKE).build();
//            //设置like与收藏状态
//            boolean likeState = favoriteMapper.findFavoriteByPostId(favorite).size() == 1;
//            favorite.setType(PostFavoriteDto.TYPE_FAVORITE);
//            boolean favoriteState = favoriteMapper.findFavoriteByPostId(favorite).size() == 1;
//
//            //设置发帖人信息
//            User user = User.builder().id(item.getUserId()).build();
//            BeanUtils.copyProperties(userService.getUserById(user), user);
//            //查找帖子信息
//
//            List<Post> posts = postMapper.findPost(item);
//            if (posts.size() != 1) {
//                throw new RuntimeException("帖子不存在");
//
//            }
//            //修改浏览量
//            post.setViewCount(item.getViewCount() + 1);
//            if (postMapper.updatePost(post) != 1) {
//                throw new RuntimeException("浏览数修改失败");
//            }
//            PostResult<Post> postResult = PostResult.<Post>builder()
//                    .like(likeState)
//                    .userId(item.getId())
//                    .favorite(favoriteState)
//                    .post(posts.get(0))
//                    .build();
//            BeanUtils.copyProperties(user, postResult);
//            postResultList.add(postResult);
//
//
//        });
//        System.out.println(postResultList);
//        //通过帖子id查找帖子标签
//        System.out.println(post);
//        List<Tag> tagList = tagMapper.getPostTag(post);
//        Map<String, Object> data = new HashMap<>();
//        data.put("postDto", postResultList);
//        data.put("tagList", tagList);
//        return data;
    }

    //获取帖子列表
    public Map<String, Object> listPost(PostSearchRequestDto postSearchRequestDto, Pagination pagination, int type, String token) {
        Post post = new Post();
        BeanUtils.copyProperties(postSearchRequestDto, post);

        List<Post> postList = postMapper.findListPost(post, pagination, type, postSearchRequestDto.getState());
        System.out.println(postList);

        List<PostResult<Post>> postResultList = new ArrayList<>();
        //循环添加每一条帖子的数据

        int userId = 0;
        if (token != null && !token.equals("")) {
            if (token.length() == 32) {
                Integer tokenMapperGetUserId = tokenMapper.find(token);
                if (tokenMapperGetUserId != null) {
                    userId = tokenMapperGetUserId;
                }
            }
        }
        int finalUserId = userId;
        postList.forEach(item -> {

            //查看收藏与喜欢状态
            Favorite favorite = Favorite.builder()
                    .userId(finalUserId)
                    .postId(item.getId())
                    .type(PostFavoriteDto.TYPE_LIKE).build();
            //设置like与收藏状态
            boolean likeState;
            boolean favoriteState;

            if (finalUserId == 0) {
                likeState = false;
                favoriteState = false;
            } else {
                likeState = favoriteMapper.findFavoriteByPostId(favorite).size() == 1;
                favorite.setType(PostFavoriteDto.TYPE_FAVORITE);
                favoriteState = favoriteMapper.findFavoriteByPostId(favorite).size() == 1;
            }

            //设置发帖人信息
            User user = User.builder().id(item.getUserId()).build();
            BeanUtils.copyProperties(userService.getUserById(user), user);
            System.out.println(user);
            //查找帖子信息
//
//            List<Post> posts = postMapper.findPost(item);
//            if (posts.size() != 1) {
//                throw new RuntimeException("帖子不存在");
//            }

            PostResult<Post> postResult = PostResult.<Post>builder()
                    .like(likeState)
                    .userId(item.getUserId())
                    .favorite(favoriteState)
                    .post(item)
                    .build();
            BeanUtils.copyProperties(user, postResult);
            postResultList.add(postResult);
        });

        Map<String, Object> data = new HashMap<>();
        BeanUtils.copyProperties(pagination, postSearchRequestDto);
        data.put("postSearchDto", postSearchRequestDto);
        postSearchRequestDto.setTotal(postMapper.findListPostTotal(post, pagination, type, postSearchRequestDto.getState()));
        data.put("postDtoList", postResultList);
        return data;
    }
}
