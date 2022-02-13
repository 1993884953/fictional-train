package com.example.myJFrame.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserList {
        //            "id": 10784,
//            "uid": 39083,
//            "nickname": "林文强",
//            "avatar": "https://www.daimaku.net/themes/images/avatar/8.jpeg",
//            "mobile": "",
//            "created_at": "2021-08-13 11:33:02",
//            "updated_at": "2021-10-08 18:50:17",
//            "avatar_url": "https://www.daimaku.net/themes/images/avatar/8.jpeg"\
        private int id;
        private int uid;
        private String nickname;
        private String avatar;
        private String mobile;
        private String created_at;
        private String updated_at;
        private String avatar_url;
}
