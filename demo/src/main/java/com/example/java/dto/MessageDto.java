package com.example.java.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageDto {

    private String id;
    private String sender_id;
    private String receiver_id;
    private String receiver_type;
    private String type;
    private Content content;
    private String created_at;
}
