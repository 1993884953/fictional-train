package com.example.gui.util;


import com.example.gui.entity.Token;
import com.example.gui.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserFoo {
    private boolean status;
    private User data;
    private String code;
}
