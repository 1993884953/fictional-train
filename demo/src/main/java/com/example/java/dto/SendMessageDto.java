package com.example.java.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SendMessageDto {
    private String status;
    private List<MessageDto> data;
    private String code;
}
