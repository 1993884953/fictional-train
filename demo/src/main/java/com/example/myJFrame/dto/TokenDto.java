package com.example.myJFrame.dto;

import com.example.myJFrame.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private boolean status;
    private Token data;
    private String code;
}
