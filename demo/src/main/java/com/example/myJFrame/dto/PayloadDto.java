package com.example.myJFrame.dto;

import com.example.myJFrame.dto.MessageDto;
import com.example.myJFrame.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayloadDto {
    private String action;
    private Message message;
//  "payload":{
//                    "action":1,
//                        "message":{
//                             "id":16661,
//                            "sender_id":10745,
//                            "receiver_id":10745,
//                            "receiver_type":1,
//                            "type":1,
//                            "created_at":"2021-10-11 11:23:02",
//                            "content":{
//                                 "text":"电池"
//                    }
//                }
//            }
}