package com.example.token.mapper;

import com.example.token.dto.SmsCreateRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//查询sms表相关
//@Mapper
public interface SmsMapper{

    //@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")//获取自增Id
    //新增sms数据,自主获取并且更改主键
    @Insert("INSERT INTO sms (mobile,verification_code,verification_key) VALUES (#{mobile},#{verificationCode},#{verificationKey})")
    int smsCreate(SmsCreateRequest smsCreateRequest);
    //使用key查询code，手机号等信息
    @Select("SELECT * FROM sms WHERE verification_key=#{id} ")
    SmsCreateRequest smsFind(@Param("id") String id);
}
