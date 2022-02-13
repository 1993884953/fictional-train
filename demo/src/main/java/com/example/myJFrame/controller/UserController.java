package com.example.myJFrame.controller;

import com.example.myJFrame.service.UserService;

import java.io.IOException;

public class UserController {
    public void getUser(String token) throws IOException {
        System.out.println(new UserService().getUser(token));
    }
}
