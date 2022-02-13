package com.example.java.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
//    {
//        "status": true,
//            "data": {
//        "id": 10745,
//                "uid": 33071,
//                "username": "",
//                "mobile": "",
//                "email": "",
//                "nickname": "火星冻梨",
//                "avatar": "https://www.daimaku.net/themes/images/avatar/3.jpeg",
//                "status": 10,
//                "created_at": "2021-05-11 16:19:58",
//                "updated_at": "2021-10-08 11:24:02",
//                "avatar_url": "https://www.daimaku.net/themes/images/avatar/3.jpeg"
//    },
//        "code": "0"
    private boolean status;
    private User data;
    private String code;

}
