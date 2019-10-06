package com.ww.springbootcommunity.mapper;


import com.ww.springbootcommunity.dto.QuestionDTO;
import com.ww.springbootcommunity.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,create_time,update_time,creator,comment_count,view_count,like_count,tag) values (#{title},#{description},#{createTime},#{updateTime},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void insert(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> querylist(Integer offset,Integer size);
    //List<Question> querylist(@Param(value = "offset") Integer offset,Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    //List<Question> queryUserByIdlist(Integer userId, Integer offset,Integer size);
    List<Question> queryUserByIdlist(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset,Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countUserById(@Param(value = "userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question getById(Integer id);

    @Update("update question set title = #{title},description = #{description},create_time = #{createTime},update_time = #{updateTime},creator = #{creator},comment_count = #{commentCount},view_count = #{viewCount},like_count =#{likeCount},tag = #{tag} where id = #{id}")
    void update(Question question);
}
