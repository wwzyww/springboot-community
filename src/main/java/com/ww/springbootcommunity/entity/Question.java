package com.ww.springbootcommunity.entity;


import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private long createTime;
    private long updateTime;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

}

