package com.ww.springbootcommunity.mapper;

import com.ww.springbootcommunity.entity.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;



@Mapper
public interface NotificationMapper {



    @Select("select * from notification where notifier = #{userId} order by id desc limit #{offset},#{size}")
    List<Notification> queryUserByIdlist(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset, Integer size);

    @Select("select count(1) from notification where notifier = #{userId}")
    Integer countUserById(@Param(value = "userId") Integer userId);



    /*---------*/


    /**
     * 查询所有
     * @param notification
     * @return
     */
    List<Notification> queryNotificationList(Notification notification);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Notification queryNotificationById(Integer id);

    /**
     * 新增
     * @param notification
     */
    void insertNotification(Notification notification);
    /**
     * 修改
     * @param notification
     */
    void updateNotification(Notification notification);

    /**
     * 根据id删除
     * @param id
     */
    void deleteNotificationById(Integer id);

    /**
     * 显示未读消息总数
     * @param id
     * @return
     */
    Integer unreadCount(Integer id);

}
