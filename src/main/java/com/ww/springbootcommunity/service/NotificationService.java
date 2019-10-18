package com.ww.springbootcommunity.service;


import com.ww.springbootcommunity.dto.NotificationDTO;
import com.ww.springbootcommunity.dto.PageDTO;
import com.ww.springbootcommunity.entity.Notification;
import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.enums.NotificationStatusEnum;
import com.ww.springbootcommunity.enums.NotificationTypeEnum;
import com.ww.springbootcommunity.mapper.NotificationMapper;
import com.ww.springbootcommunity.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;


    public PageDTO list(Integer userId, Integer page, Integer size) {

        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();

        Integer totalPage;
        Integer totalCount = notificationMapper.countUserById(userId);
        if (totalCount % size ==0){
            totalPage = totalCount /size;
        }else {
            totalPage = totalCount /size + 1;
        }
        if(page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageDTO.setPageDTO(totalPage,page);


        Integer offset = size * (page - 1);

        List<Notification> notifications = notificationMapper.queryUserByIdlist(userId,offset,size);
        if(notifications.size() == 0) {
            return pageDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        pageDTO.setData(notificationDTOS);

        return pageDTO;

    }

    public Integer unreadCount(Integer id) {
        return notificationMapper.unreadCount(id);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.queryNotificationById(id);
        System.out.println(notification);

        //标记已读
        notification.setId(id);
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        System.out.println(notification);
        notificationMapper.updateNotification(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));



        return notificationDTO;
    }
}
