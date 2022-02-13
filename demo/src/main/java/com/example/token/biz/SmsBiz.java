package com.example.token.biz;
import com.example.token.dto.SmsCreateRequest;

import java.util.Map;

public interface  SmsBiz {
    Map<String, String> smsCreate(SmsCreateRequest smsCreateRequest);
}
