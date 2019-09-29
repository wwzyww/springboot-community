package com.ww.springbootcommunity.mapper;


import com.ww.springbootcommunity.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 保存用户
     * @param user
     */
    @Insert("insert into user (account_id,name,token,create_time,update_time) values (#{accountId},#{name},#{token},#{createTime},#{updateTime})")
    void Insert(User user);

}
