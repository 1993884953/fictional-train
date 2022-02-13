package com.example.myJFrame.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDto {
    private String type;
    private PayloadDto payload;
}
