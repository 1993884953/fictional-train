package com.example.gui.util;

import com.example.gui.entity.UserList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListFoo {
    private boolean status;
    private UserList[] data;
    private String code;

    public boolean getStatus() { return status; }
    public void setStatus(boolean value) { this.status = value; }

    public UserList[] getData() { return data; }
    public void setData(UserList[] value) { this.data = value; }

    public String getCode() { return code; }
    public void setCode(String value) { this.code = value; }
}
