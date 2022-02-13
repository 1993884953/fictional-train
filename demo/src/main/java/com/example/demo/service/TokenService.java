package com.example.demo.service;

import com.example.demo.dto.TokenCreateDto;
import com.example.demo.entity.Token;
import com.example.demo.entity.User;
import com.example.demo.interceptor.UserContext;
import com.example.demo.mapper.TokenMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TokenService {
    @Resource
    TokenMapper tokenMapper;
    @Resource
    UserMapper userMapper;

    //创建token，返回token数据
    public Map<String, String> create(TokenCreateDto tokenCreateDto) {
        //接收参数并转化
        User user = new User();
        BeanUtils.copyProperties(tokenCreateDto, user);

        //校验账号密码
        if (userMapper.find(user) == null) {
            throw new RuntimeException("账号或密码不匹配");
        }
        //获取从数据库加载回来的user
        BeanUtils.copyProperties(userMapper.find(user), user);

        //创建token，返回token数据
        System.out.println("删除了" + tokenMapper.delete(user.getId()) + "条token");

        String tokenStr = UUID.randomUUID().toString().replaceAll("-", "");
        Token token = Token.builder().token(tokenStr).userId(user.getId()).build();
        if (tokenMapper.create(token) != 1) {
            throw new RuntimeException("token创建失败");
        }
        //创建map类型data数据
        Map<String, String> data = new HashMap<>();
        data.put("token", tokenStr);
        return data;
    }

    //删除token
    public Map<String, String> delete(String token) {
        if (token == null || token.length() != 32) {
            throw new RuntimeException("token格式不正确");
        }
        if (tokenMapper.find(token) != UserContext.getUser().getId()) {
            throw new RuntimeException("无效token");
        }
        if (tokenMapper.delete(UserContext.getUser().getId()) == 0) {
            throw new RuntimeException("token删除失败");
        }
        System.out.println(UserContext.getUser());
        return null;
    }

}
