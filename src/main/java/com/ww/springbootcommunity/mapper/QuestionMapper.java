package com.ww.springbootcommunity.mapper;


import com.ww.springbootcommunity.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,create_time,update_time,creator,comment_count,view_count,like_count,tag) values (#{title},#{description},#{createTime},#{updateTime},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void insert(Question question);

    @Select("select * from question limit #{offset},#{size}")
    //List<Question> querylist(Integer offset,Integer size);
    List<Question> querylist(@Param(value = "offset") Integer offset,Integer size);

    @Select("select count(1) from question")
    Integer count();

}
