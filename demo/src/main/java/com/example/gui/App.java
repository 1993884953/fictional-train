package com.example.gui;

import com.example.gui.service.UserService;

public class App {
    public static void main(String[] args) {
        UserService userService=new UserService();
        System.out.println(userService.login());
    }
}
