package com.ww.springbootcommunity.enums;

public enum NotificationStatusEnum {
    UNREAD(0),  //未读
    READ(1);    //已读

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
