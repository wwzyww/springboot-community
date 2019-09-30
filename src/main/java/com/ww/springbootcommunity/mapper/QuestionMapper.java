package com.ww.springbootcommunity.mapper;


import com.ww.springbootcommunity.entity.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,create_time,update_time,creator,comment_count,view_count,like_count,tag) values (#{title},#{description},#{createTime},#{updateTime},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    public void insert(Question question);

}
