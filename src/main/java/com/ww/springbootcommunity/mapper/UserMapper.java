package com.ww.springbootcommunity.mapper;


import com.ww.springbootcommunity.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface UserMapper {

    /**
     * 保存用户
     * @param user
     */
    @Insert("insert into user (account_id,name,token,create_time,update_time) values (#{accountId},#{name},#{token},#{createTime},#{updateTime})")
    void Insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}
