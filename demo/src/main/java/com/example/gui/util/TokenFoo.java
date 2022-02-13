package com.example.gui.util;

import com.example.gui.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenFoo {
    private boolean status;
    private Token data;
    private String code;
}
