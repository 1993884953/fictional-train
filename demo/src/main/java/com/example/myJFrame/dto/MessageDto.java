package com.example.myJFrame.dto;

import com.example.myJFrame.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDto {
    private boolean status;
    private List<Message> data;
    private String code;
}