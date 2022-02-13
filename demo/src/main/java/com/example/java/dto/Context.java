package com.example.java.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Context {
//            "id": 10784,
//            "uid": 39083,
//            "nickname": "林文强",
//            "avatar": "https://www.daimaku.net/themes/images/avatar/8.jpeg",
//            "mobile": "",
//            "created_at": "2021-08-13 11:33:02",
//            "updated_at": "2021-10-08 18:50:17",
//            "avatar_url": "https://www.daimaku.net/themes/images/avatar/8.jpeg"\
    private String id;
    private String uid;
    private String nickname;
    private String avatar;
    private String mobile;
    private String created_at;
    private String updated_at;
    private String avatar_url;
}
