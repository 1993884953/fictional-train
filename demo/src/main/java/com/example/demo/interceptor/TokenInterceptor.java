package com.example.demo.interceptor;

import com.example.demo.entity.User;
import com.example.demo.mapper.TokenMapper;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//告诉框架这个是token拦截起的组件
@Component
//创建一个全局拦截的类，实现HandlerInterceptor这个接口
public class TokenInterceptor implements HandlerInterceptor {
    @Resource
    TokenMapper tokenMapper;
    @Resource
    UserMapper userMapper;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getParameter("token");

        if (token == null|| token.equals("")) {
            throw new RuntimeException("无token信息");
        }

//        System.out.println(tokenMapper.find(token));
        //去token表查，得到userId
        Integer userId=tokenMapper.find(token);
        if (userId==null){
            throw new RuntimeException("无效的token");
        }
        System.out.println("拦截器生效,token:" + userId);
        //去user表查，得到user类
        User user=User.builder().id(userId).build();
        //查到则储存User信息
        UserContext.setUser(userMapper.find(user));
        return true;
    }
}


