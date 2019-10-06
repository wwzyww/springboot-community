package com.ww.springbootcommunity.service;

import com.ww.springbootcommunity.entity.User;
import com.ww.springbootcommunity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
       User dbUser =  userMapper.findByAccountId(user.getAccountId());
       if(dbUser == null) {
           //插入
           user.setCreateTime(System.currentTimeMillis());//System.currentTimeMillis产生一个当前的毫秒时间
           user.setUpdateTime(user.getCreateTime());
           userMapper.Insert(user);
       }else {
           //更新
           dbUser.setCreateTime(System.currentTimeMillis());
           dbUser.setAvatarUrl(user.getAvatarUrl());
           dbUser.setName(user.getName());
           dbUser.setToken(user.getToken());
           userMapper.update(dbUser);
       }
    }
}
