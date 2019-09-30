package com.ww.springbootcommunity.entity;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private long createTime;
    private long updateTime;
    private String bio;
    private String avatarUrl;


}
