package com.ww.springbootcommunity.entity;

import lombok.Data;

@Data
public class Comment {
    private Integer id;

    private Integer parentId;

    private Integer type;

    private Integer commentator;

    private Long createTime;

    private Long updateTime;

    private Integer commentCount;

    private Integer likeCount;

    private String content;
}