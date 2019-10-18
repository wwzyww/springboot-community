package com.ww.springbootcommunity.dto;

import com.ww.springbootcommunity.entity.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;

    private Integer parentId;

    private Integer type;

    private Integer commentator;

    private Long createTime;

    private Long updateTime;

    private Integer commentCount;

    private Integer likeCount;

    private String content;

    private User user;
}