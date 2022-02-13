package com.example.token.interceptor;//package com.example.demo.interceptor;

import com.example.token.entity.User;

//安全线程存放user信息，线程不安全
public class UserContext {

    //创建一个本地线程存放user信息
    private static final ThreadLocal<User> current = new ThreadLocal<User>();

    public static void setUser(User user) {
        current.set(user);
    }

    public static User getUser() {
        return current.get();
    }
}
