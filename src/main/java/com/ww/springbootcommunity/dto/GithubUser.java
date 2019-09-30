package com.ww.springbootcommunity.dto;


import lombok.Data;

/**
 * 获取Github用户的信息
 */
@Data
public class GithubUser {

    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;


}
