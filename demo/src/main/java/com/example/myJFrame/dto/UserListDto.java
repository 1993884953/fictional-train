package com.example.myJFrame.dto;

import com.example.myJFrame.entity.UserList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserListDto {
        private String status;
        private List<UserList> data;
        private String code;
}
