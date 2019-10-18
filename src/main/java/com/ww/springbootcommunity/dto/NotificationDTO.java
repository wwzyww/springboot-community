package com.ww.springbootcommunity.dto;

import com.ww.springbootcommunity.entity.User;
import lombok.Data;

@Data
public class NotificationDTO {

    private Integer id;

    private Long createTime;

    private Integer status;

    private Integer notifier;

    private String notifierName;

    private Integer outerId;

    private String outerTitle;

    private Integer type;

    private String typeName;

}
