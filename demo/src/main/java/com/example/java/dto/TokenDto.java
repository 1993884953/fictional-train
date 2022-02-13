package com.example.java.dto;

import jiemianbao.Denglu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@AllArgsConstructor
public class TokenDto {


        private boolean status;
        private Token data;
        private String code;

}
