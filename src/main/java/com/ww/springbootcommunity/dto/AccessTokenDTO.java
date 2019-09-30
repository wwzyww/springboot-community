package com.ww.springbootcommunity.dto;

import lombok.Data;

/**
 * 实现用Github登录 需要用到的信息字段
 */
@Data
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;

}
