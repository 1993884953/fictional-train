package com.example.myJFrame.dto;

import com.example.myJFrame.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private boolean status;
    private User data;
    private String code;
}
