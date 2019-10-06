package com.ww.springbootcommunity.mapper;


import com.ww.springbootcommunity.entity.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {

    /**
     * 保存用户
     * @param user
     */
    @Insert("insert into user (account_id,name,token,create_time,update_time,bio,avatar_url) values (#{accountId},#{name},#{token},#{createTime},#{updateTime},#{bio},#{avatarUrl})")
    void Insert(User user);

    /**
     *根据token 查询用户
     * @param token
     * @return
     */
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    /**
     * 根据id 查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(String accountId);

    @Update("update user set account_id = #{accountId},name = #{name},token = #{token},create_time = #{createTime},update_time = #{updateTime},bio = #{bio},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User dbUser);
}
