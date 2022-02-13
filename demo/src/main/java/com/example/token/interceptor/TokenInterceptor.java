package com.example.token.interceptor;

import com.example.token.entity.User;
import com.example.token.mapper.TokenMapper;
import com.example.token.mapper.UserMapper;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getParameter("token");
        System.out.println("拦截器生效,token:" + token);
        if (token == null|| token.equals("")) {
            throw new RuntimeException("无token信息");
        }
        //去token表查，得到userId
        String userId= tokenMapper.tokenFind(token);
        //去user表查，得到user类
        User user = userMapper.userFind(userId);
        if (user == null) {
            //查不到抛出异常
            throw new RuntimeException("token无效");
        }
        System.out.println(user);
        //查到则储存User信息
        UserContext.setUser(user);
        return true;
    }
}


