package com.example.myJFrame.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {
        private int id;
        private int sender_id;
        private int receiver_id;
        private int receiver_type;
        private int type;
        private Content content;
        private String created_at;
}
