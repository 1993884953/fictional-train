package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostFavoriteDto {
    private int id;
    private int userId;
    private int postId;

    private int type;

    public final static int TYPE_LIKE=1;
    public final static int TYPE_FAVORITE=2;
}
