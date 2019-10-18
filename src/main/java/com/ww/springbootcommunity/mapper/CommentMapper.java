package com.ww.springbootcommunity.mapper;


import com.ww.springbootcommunity.entity.Comment;

import java.util.List;

public interface CommentMapper {


    /**
     * 查询所有
     * @param comment
     * @return
     */
    List<Comment> queryCommentList(Comment comment);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Comment queryCommentById(Integer id);

    /**
     * 新增
     * @param comment
     */
    void insertComment(Comment comment);
    /**
     * 修改
     * @param comment
     */
    void updateComment(Comment comment);

    /**
     * 根据id删除
     * @param id
     */
    void deleteCommentById(Integer id);

    /**
     * 累加评论数  这样防止高并发
     * @param comment
     */
    void incCommentCount(Comment comment);

}
